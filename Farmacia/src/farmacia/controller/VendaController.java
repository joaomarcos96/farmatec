package farmacia.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import farmacia.dao.DAO;
import farmacia.dao.ProdutoDao;
import farmacia.dao.VendaDao;
import farmacia.modelo.Cliente;
import farmacia.modelo.Funcionario;
import farmacia.modelo.Produto;
import farmacia.modelo.ProdutoVenda;
import farmacia.modelo.Venda;
import farmacia.utils.JPAUtil;

@ViewScoped
@ManagedBean
public class VendaController {
	private Venda venda = new Venda();
	private Integer id_prod, id_fun, id_cli, qtd;
	
	public void adicionarItem(){
		Produto produto = new DAO<Produto>(Produto.class).buscarPorId(id_prod);
		
		if(produto.getEstoque() >= qtd && produto.getEstoque() - qtd >= produto.getEstoque_minimo()){
			ProdutoVenda item = new ProdutoVenda();
			item.setProduto(produto);
			item.setQuantidade(qtd);
			item.setPreco(produto.getValor());
			item.setVenda(venda);
			venda.add(item);
		}
		else {
			FacesContext.getCurrentInstance().addMessage("venda", new FacesMessage("Estoque Suficiente."));
			return;
		}
		
		qtd = 0;
		id_prod = null;
	}
	
	public void removerItem(ProdutoVenda item){
		venda.getItens().remove(item);
	}
	
	public void baixaEstoque(EntityManager em){
		ProdutoDao dao = new ProdutoDao();
		for(ProdutoVenda item : venda.getItens()){
			Produto produto = item.getProduto();
			dao.atualizaEstoque(produto, -item.getQuantidade(), em);
		}
	}
	
	public ProdutoVenda temEstoque(EntityManager em){
		ProdutoVenda item = null;
		
		DAO<Produto> dao = new DAO<Produto>(Produto.class);
		
		for(ProdutoVenda i : venda.getItens()){
			Produto produto = dao.buscarPorId(i.getProduto().getId(), em);
			if(i.getQuantidade() > produto.getEstoque()){
				item = i;
				break;
			}
		}
		
		return item;
		
	}
	
	public void gravar(){
		if(venda.getItens().isEmpty()){
			FacesContext.getCurrentInstance().addMessage("venda", new FacesMessage("Nenhum item na venda."));
			return;
		}
		
		EntityManager em = JPAUtil.getEntityManager();
		
		try {
			em.getTransaction().begin();
			
			Cliente cli = new DAO<Cliente>(Cliente.class).buscarPorId(this.id_cli, em);
			venda.setCliente(cli);
			
			Funcionario fun = new DAO<Funcionario>(Funcionario.class).buscarPorId(this.id_fun, em);
			venda.setFuncionario(fun);
			
			ProdutoVenda item = temEstoque(em);
			
			if(item != null){
				FacesContext.getCurrentInstance().addMessage("venda", new FacesMessage("Estoque de " + item.getProduto().getNome() + " insuficiente."));
				return;
			}
			
			baixaEstoque(em);
			
			new DAO<Venda>(Venda.class).adicionar(venda, em);
			
			em.getTransaction().commit();
			
			FacesContext.getCurrentInstance().addMessage("venda", new FacesMessage("Venda cadastrada com sucesso."));		
			
		} catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
			FacesContext.getCurrentInstance().addMessage("venda", new FacesMessage("Erro na transação."));			
		}
		
		em.close();
		
		this.venda = new Venda();
		this.id_cli = null;
		this.id_fun = null;
		this.id_prod = null;
		this.qtd = 0;
	}
	
	public void carregar(Venda venda){
		this.venda = venda;
		this.id_cli = venda.getCliente().getId();
		this.id_fun = venda.getFuncionario().getId();
	}
	
	public void remover(Venda venda){
		new DAO<Venda>(Venda.class).remover(venda);
		FacesContext.getCurrentInstance().addMessage("venda", new FacesMessage("Venda removida com sucesso."));
	}
	
	// getters e setters
	public List<Venda> getTodasVendas(){
		return new VendaDao().listaVendas();
	}
	
	public List<Cliente> getTodosClientes(){
		return new DAO<Cliente>(Cliente.class).buscarTodos();
	}
	
	public List<Funcionario> getTodosFuncionarios(){
		return new DAO<Funcionario>(Funcionario.class).buscarTodos();
	}
	
	public List<Produto> getProdutos() {
		return new DAO<Produto>(Produto.class).buscarTodos();
	}
	
	public Integer getId_prod() {
		return id_prod;
	}
	
	public void setId_prod(Integer id_prod) {
		this.id_prod = id_prod;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Integer getId_fun() {
		return id_fun;
	}

	public void setId_fun(Integer id_fun) {
		this.id_fun = id_fun;
	}

	public Integer getId_cli() {
		return id_cli;
	}

	public void setId_cli(Integer id_cli) {
		this.id_cli = id_cli;
	}
	
	public Integer getQtd() {
		return qtd;
	}
	
	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}
	
	public List<ProdutoVenda> getItens(){
		return venda.getItens();
	}
}
