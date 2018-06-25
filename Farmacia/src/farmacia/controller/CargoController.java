package farmacia.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import farmacia.dao.DAO;
import farmacia.modelo.Cargo;

@ViewScoped
@ManagedBean
public class CargoController {
	private Cargo cargo = new Cargo();
	private DAO<Cargo> dao = new DAO<Cargo>(Cargo.class);
	
	public void gravar(){
		try {
			if(cargo.getId() == null){
				dao.adicionar(cargo);
				FacesContext.getCurrentInstance().addMessage("cargo", new FacesMessage("Cargo cadastrado com sucesso."));
			}
			else {
				dao.atualizar(cargo);
				FacesContext.getCurrentInstance().addMessage("cargo", new FacesMessage("Cargo atualizado com sucesso."));
			}
			this.cargo = new Cargo();
		} catch(Exception e){
			FacesContext.getCurrentInstance().addMessage("cargo", new FacesMessage("Cargo já existe."));
			e.printStackTrace();
		}
	}
	
	public void carregar(Cargo cargo){
		this.cargo = cargo;
	}
	
	public void remover(Cargo cargo) {
		try {
			dao.remover(cargo);
			FacesContext.getCurrentInstance().addMessage("cargo", new FacesMessage("Cargo removido com sucesso."));
		} catch(Exception e){
			FacesContext.getCurrentInstance().addMessage("cargo", new FacesMessage("Cargo não pode ser removido pois está associado a um funcionário."));
			e.printStackTrace();
		}
	}
	
	public String formCargo(){
		return "cargo?faces-redirect=true";
	}
	
	// getters e setters
	public Cargo getCargo() {
		return cargo;
	}
	
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	public List<Cargo> getTodosCargos(){
		return dao.buscarTodos(); 
	}

}
