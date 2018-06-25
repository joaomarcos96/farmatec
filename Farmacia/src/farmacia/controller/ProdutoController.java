package farmacia.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import farmacia.dao.DAO;
import farmacia.modelo.Produto;

@ViewScoped
@ManagedBean
public class ProdutoController {
	private Produto produto = new Produto();
	private DAO<Produto> dao = new DAO<Produto>(Produto.class);
	
	public void gravar() {
		if(this.produto.getId() == null) {
			dao.adicionar(this.produto);
		} else {
			dao.atualizar(this.produto);
		}
		this.produto = new Produto();
	}

	public void carregar(Produto prod) {
		this.produto = prod;
	}

	public void remover(Produto prod) {
		try {
			dao.remover(prod);
		} catch(Exception e){
			FacesContext.getCurrentInstance().addMessage("produto", new FacesMessage("Produto não pode ser removido pois está associado a uma compra e/ou uma venda."));
			e.printStackTrace();
		}
	}
	
	public String formProduto(){
		return "produto?faces-redirect=true";
	}
	
	// getters e setters
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getTodosProdutos() {
		return dao.buscarTodos();
	}
}
