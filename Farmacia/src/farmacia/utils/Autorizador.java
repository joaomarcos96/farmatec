package farmacia.utils;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import farmacia.modelo.Funcionalidade;
import farmacia.modelo.Usuario;

public class Autorizador implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		// pegar o nome da página
		FacesContext context = event.getFacesContext();
		String nomePagina = context.getViewRoot().getViewId();
		
		if (nomePagina.equals("/login.xhtml")) {
			return;
		}
		
		// outra página
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

		if (usuarioLogado != null) {
			
			// a página principal pode ser acessada
			if (nomePagina.equals("/produto.xhtml")){
				return;
			}
			
			// caso seja, obtém funcionalidade da sessão
			Funcionalidade funcionalidade = (Funcionalidade)
			context.getExternalContext().getSessionMap().get(nomePagina);
			
			if (funcionalidade != null){
				return; 
			}
			
			NavigationHandler handler = context.getApplication().getNavigationHandler();
			handler.handleNavigation(context, null, "/produto?faces-redirect=true");
			
			context.renderResponse();
			return;
		}
		
		if(nomePagina.equals("/novo_cadastro.xhtml") || nomePagina.equals("/cadastro_cliente_pf.xhtml") || nomePagina.equals("/cadastro_cliente_pj.xhtml")){
			return;
		}
		else {
			// usuário não está logado, deve fazer login
			NavigationHandler handler = context.getApplication().getNavigationHandler();
			handler.handleNavigation(context, null, "/login?faces-redirect=true");
		}
		
		context.renderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
