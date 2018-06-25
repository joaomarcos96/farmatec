package farmacia.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import farmacia.dao.UsuarioDao;
import farmacia.dao.GrupoDao;
import farmacia.modelo.Funcionalidade;
import farmacia.modelo.Usuario;
import farmacia.modelo.Grupo;
import farmacia.utils.Utils;

@ManagedBean
@ViewScoped
public class LoginController {
	private Usuario usuario = new Usuario();
			
	public String efetuarLogin(){
		usuario.setSenha(Utils.toMD5(usuario.getSenha()));
		usuario = new UsuarioDao().buscarPorLoginESenha(usuario);
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(usuario != null) {
			
			context.getExternalContext().getSessionMap().put("usuarioLogado", usuario);

			Grupo grupo = new GrupoDao().getGrupoFuncionalidades(usuario.getGrupo());
			
			for(Funcionalidade f : grupo.getFuncionalidades()){
				context.getExternalContext().getSessionMap().put(f.getPagina(), f);
			}
			
			return "produto?faces-redirect=true";
		}
		else {
			context.getExternalContext().getFlash().setKeepMessages(true);

			context.addMessage(null, new FacesMessage("Login/senha incorretos ou usuário não encontrado."));
			
			return "login?faces-redirect=true";
		}
		
	}
	
	public String deslogar(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		context.getExternalContext().getSessionMap().clear();
		
		return "login?faces-redirect=true";
	}
	
	// getters e setters
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
