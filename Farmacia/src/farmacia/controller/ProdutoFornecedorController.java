package farmacia.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import farmacia.dao.DAO;
import farmacia.dao.ProdutoFornecedorControllerDao;
import farmacia.modelo.Fornecedor;
import farmacia.modelo.Produto;
import farmacia.modelo.ProdutoFornecedor;

@ManagedBean
@ViewScoped
public class ProdutoFornecedorController {
	private ProdutoFornecedor prod_fornecedor = new ProdutoFornecedor();
	private Integer id_prod, id_forn;
	private DAO<ProdutoFornecedor> dao = new DAO<ProdutoFornecedor>(ProdutoFornecedor.class);
	
	public void gravar(){
		Produto produto = new DAO<Produto>(Produto.class).buscarPorId(id_prod);
		Fornecedor fornecedor = new DAO<Fornecedor>(Fornecedor.class).buscarPorId(id_forn);
		
		prod_fornecedor.setProduto(produto);
		prod_fornecedor.setFornecedor(fornecedor);
		
		if(prod_fornecedor.getId() == null){
			produto.setEstoque(produto.getEstoque() + prod_fornecedor.getQuantidade());
			produto.setValor((produto.getValor() + prod_fornecedor.getPreco() * 1.3) / 2.0);
			dao.adicionar(prod_fornecedor);
			FacesContext.getCurrentInstance().addMessage("produto_fornecedor", new FacesMessage("Compra cadastrada com sucesso."));
		}
		else {
			ProdutoFornecedor prod_forn = new DAO<ProdutoFornecedor>(ProdutoFornecedor.class).buscarPorId(prod_fornecedor.getId());
			produto.setEstoque(produto.getEstoque() - prod_forn.getQuantidade() + prod_fornecedor.getQuantidade());
			
			Double valor_atual = produto.getValor() * 2.0 - prod_forn.getPreco() * 1.3;
			
			produto.setValor((valor_atual + prod_fornecedor.getPreco() * 1.3) / 2.0);

			dao.atualizar(prod_fornecedor);
			FacesContext.getCurrentInstance().addMessage("produto_fornecedor", new FacesMessage("Compra atualizada com sucesso."));
		}
		
		// atualiza o produto //
		ProdutoController prod_cont = new ProdutoController();
		prod_cont.setProduto(produto);
		
		prod_cont.gravar();
		//
		
		this.prod_fornecedor = new ProdutoFornecedor();
		this.id_prod = null;
		this.id_forn = null;
	}
	
	public void carregar(ProdutoFornecedor prod_fornecedor){
		this.prod_fornecedor = prod_fornecedor;
		this.id_prod = prod_fornecedor.getProduto().getId();
		this.id_forn = prod_fornecedor.getFornecedor().getId();
	}
	
	public void remover(ProdutoFornecedor prod_fornecedor){
		
		Produto produto = new DAO<Produto>(Produto.class).buscarPorId(prod_fornecedor.getProduto().getId());
		Fornecedor fornecedor = new DAO<Fornecedor>(Fornecedor.class).buscarPorId(prod_fornecedor.getFornecedor().getId());
		
		prod_fornecedor.setProduto(produto);
		prod_fornecedor.setFornecedor(fornecedor);
		
		ProdutoFornecedor prod_forn = new DAO<ProdutoFornecedor>(ProdutoFornecedor.class).buscarPorId(prod_fornecedor.getId());
		
		produto.setEstoque(produto.getEstoque() - prod_forn.getQuantidade());
		Double valor_atual = produto.getValor() * 2.0 - prod_forn.getPreco() * 1.3;	
		produto.setValor(valor_atual);	
		
		dao.remover(prod_fornecedor);
		
		FacesContext.getCurrentInstance().addMessage("produto_fornecedor", new FacesMessage("Compra removida com sucesso."));
		
		// atualiza o produto
		ProdutoController prod_cont = new ProdutoController();
		prod_cont.setProduto(produto);
		
		prod_cont.gravar();
		//
	}
	
	// getters e setters
	public ProdutoFornecedor getProd_fornecedor() {
		return prod_fornecedor;
	}
	
	public void setProd_fornecedor(ProdutoFornecedor prod_fornecedor) {
		this.prod_fornecedor = prod_fornecedor;
	}
	
	public Integer getId_prod() {
		return id_prod;
	}

	public void setId_prod(Integer id_prod) {
		this.id_prod = id_prod;
	}

	public Integer getId_forn() {
		return id_forn;
	}

	public void setId_forn(Integer id_forn) {
		this.id_forn = id_forn;
	}

	public List<ProdutoFornecedor> getTodosProdutoFornecedor(){
		return new ProdutoFornecedorControllerDao().listaProdutoFornecedor();
	}
	
	public List<Produto> getTodosProdutos(){
		return new DAO<Produto>(Produto.class).buscarTodos();
	}
	
	public List<Fornecedor> getTodosFornecedores(){
		return new DAO<Fornecedor>(Fornecedor.class).buscarTodos();
	}
}
