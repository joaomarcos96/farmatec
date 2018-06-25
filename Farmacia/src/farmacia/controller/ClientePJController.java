package farmacia.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import farmacia.dao.DAO;
import farmacia.modelo.Cliente;
import farmacia.modelo.ClientePJ;

@ViewScoped
@ManagedBean
public class ClientePJController extends ClienteController {
	private ClientePJ clientePJ = new ClientePJ();
	
	public String cadastrar(Cliente cliente){
		super.checksESets(cliente);
		try {
			super.gravar(cliente);
			return "login?faces-redirect=true";
		} catch(Exception e){
			FacesContext.getCurrentInstance().addMessage("cliente_pj", new FacesMessage("CPF e/ou Login já existem."));
			e.printStackTrace();
		}
		this.clientePJ = new ClientePJ();
		return null;
	}
	
	@Override
	public void gravar(Cliente cliente){
		super.checksESets(cliente);
		try {
			super.gravar(cliente);
		} catch(Exception e){
			FacesContext.getCurrentInstance().addMessage("cliente_pj", new FacesMessage("CPF e/ou Login já existem."));
			e.printStackTrace();
		}
		this.clientePJ = new ClientePJ();
	}
	
	@Override
	public void carregar(Cliente cliente){
		super.carregar(cliente);
		this.clientePJ = (ClientePJ) cliente;
	}
	
	@Override
	public void remover(Cliente cliente){
		try {
			super.remover(cliente);
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("cliente_pj", new FacesMessage("Cliente não pode ser removido, está associado a uma venda."));
		}
	}
	
	// getters e setters
	public ClientePJ getClientePJ() {
		return clientePJ;
	}
	
	public void setClientePJ(ClientePJ clientePJ) {
		this.clientePJ = clientePJ;
	}
	
	public List<ClientePJ> getTodosClientesPJ(){
		return new DAO<ClientePJ>(ClientePJ.class).buscarTodos();
	}

}
