package farmacia.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import farmacia.modelo.Funcionalidade;

@ViewScoped
@ManagedBean
public class PrincipalController {
	public boolean temAcesso(String nome){
		FacesContext context = FacesContext.getCurrentInstance();
		Funcionalidade funcionalidade = (Funcionalidade) context.getExternalContext().getSessionMap().get("/" + nome + ".xhtml");
		
		if(funcionalidade != null){
			return true;
		}
		else {
			return false;
		}
	}
}
