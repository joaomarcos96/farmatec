package farmacia.controller;

import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import farmacia.dao.DAO;
import farmacia.modelo.Cliente;
import farmacia.modelo.ClientePF;
import farmacia.modelo.Genero;

@ViewScoped
@ManagedBean
public class ClientePFController extends ClienteController {
	private ClientePF clientePF = new ClientePF();
	
	public String cadastrar(Cliente cliente){
		super.checksESets(cliente);
		try {
			super.gravar(cliente);
			return "login?faces-redirect=true";
		} catch(Exception e){
			FacesContext.getCurrentInstance().addMessage("cliente_pf", new FacesMessage("CPF e/ou Login já existem."));
			e.printStackTrace();
		}
		this.clientePF = new ClientePF();
		return null;
	}
	
	@Override
	public void gravar(Cliente cliente){
		super.checksESets(cliente);
		try {
			super.gravar(cliente);
		} catch(Exception e){
			FacesContext.getCurrentInstance().addMessage("cliente_pf", new FacesMessage("CPF e/ou Login já existem."));
			e.printStackTrace();
		}
		this.clientePF = new ClientePF();
	}
	
	@Override
	public void carregar(Cliente cliente){
		super.carregar(cliente);
		this.clientePF = (ClientePF) cliente;
	}
	
	@Override
	public void remover(Cliente cliente){
		try {
			super.remover(cliente);
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("cliente_pf", new FacesMessage("Cliente não pode ser removido, está associado a uma venda."));
		}
	}
	
	// getters e setters
	public ClientePF getClientePF() {
		return clientePF;
	}
	
	public void setClientePF(ClientePF clientePF) {
		this.clientePF = clientePF;
	}
	
	public List<Genero> getGeneros(){
		return Arrays.asList(Genero.values());
	}
	
	public List<ClientePF> getTodosClientesPF(){
		return new DAO<ClientePF>(ClientePF.class).buscarTodos();
	}

}
