package farmacia.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import farmacia.dao.DAO;
import farmacia.modelo.Cliente;
import farmacia.modelo.Grupo;
import farmacia.modelo.Usuario;
import farmacia.utils.Utils;

@ViewScoped
@ManagedBean
public class ClienteController {
	private Usuario usuario = new Usuario();
	private String senha_bd;
	
	public void checksESets(Cliente cliente){
		Grupo grupo = new Grupo();
		grupo.setId(3);
		
		usuario.setGrupo(grupo);
		cliente.setUsuario(usuario);
		
		if(!cliente.getUsuario().getSenha().equals(senha_bd)){
			cliente.getUsuario().setSenha(Utils.toMD5(cliente.getUsuario().getSenha()));
		}
	}
	
	public void gravar(Cliente cliente){
		DAO<Cliente> dao = new DAO<Cliente>(Cliente.class);
		if(cliente.getUsuario().getId() == null){
			new DAO<Usuario>(Usuario.class).adicionar(usuario);
		}
		else {
			new DAO<Usuario>(Usuario.class).atualizar(usuario);
		}
		if(cliente.getId() == null){
			dao.adicionar(cliente);
			FacesContext.getCurrentInstance().addMessage("cliente_pf", new FacesMessage("Cliente cadastrado com sucesso"));
		}
		else {
			dao.atualizar(cliente);
			FacesContext.getCurrentInstance().addMessage("cliente_pf", new FacesMessage("Cliente atualizado com sucesso"));
		}
		this.usuario = new Usuario();
	}
	
	public void carregar(Cliente cliente){
		this.usuario = cliente.getUsuario();
		cliente.getUsuario().setSenha(null);
		this.senha_bd = null;
	}
	
	public void remover(Cliente cliente){
		Usuario usuario_remover = cliente.getUsuario();
		new DAO<Cliente>(Cliente.class).remover(cliente);
		new DAO<Usuario>(Usuario.class).remover(usuario_remover);
		FacesContext.getCurrentInstance().addMessage("cliente_pf", new FacesMessage("Cliente removido com sucesso"));
	}
	
	public String formCliente(){
		return "cliente?faces-redirect=true";
	}
	
	// getters e setters
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getSenha_bd() {
		return senha_bd;
	}

	public void setSenha_bd(String senha_bd) {
		this.senha_bd = senha_bd;
	}
}
