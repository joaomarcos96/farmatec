package farmacia.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import farmacia.dao.DAO;
import farmacia.modelo.Fornecedor;

@ViewScoped
@ManagedBean
public class FornecedorController {
	private Fornecedor fornecedor = new Fornecedor();
	
	public void gravar(Fornecedor fornecedor) {
		try {
			DAO<Fornecedor> dao = new DAO<Fornecedor>(Fornecedor.class);
			if(fornecedor.getId() == null){
				dao.adicionar(fornecedor);
			} else {
				dao.atualizar(fornecedor);
			}
			this.fornecedor = new Fornecedor();
		} catch(Exception e){
			FacesContext.getCurrentInstance().addMessage("fornecedor", new FacesMessage("CNPJ já existe."));
			e.printStackTrace();
		}
	}

	public void carregar(Fornecedor fornecedor){
		this.fornecedor = fornecedor;
	}
	
	public void remover(Fornecedor fornecedor){
		try {
			new DAO<Fornecedor>(Fornecedor.class).remover(fornecedor);
		} catch(Exception e){
			FacesContext.getCurrentInstance().addMessage("fornecedor", new FacesMessage("Fornecedor não pode ser removido pois está associado a uma compra."));
			e.printStackTrace();
		}
	}
	
	public String formFornecedor(){
		return "fornecedor?faces-redirect=true";
	}
	
	// getters e setters
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public List<Fornecedor> getTodosFornecedores(){
		return new DAO<Fornecedor>(Fornecedor.class).buscarTodos();
	}
}
