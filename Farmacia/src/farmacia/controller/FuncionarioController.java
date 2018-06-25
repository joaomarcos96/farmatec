package farmacia.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import farmacia.dao.DAO;
import farmacia.dao.FuncionarioDao;
import farmacia.modelo.Cargo;
import farmacia.modelo.Funcionario;
import farmacia.modelo.Genero;
import farmacia.modelo.Grupo;
import farmacia.modelo.Usuario;
import farmacia.utils.Utils;

@ViewScoped
@ManagedBean
public class FuncionarioController {
	private Funcionario funcionario = new Funcionario();
	private Integer id_cargo, id_grupo;
	private String senha_bd;
	private Usuario usuario = new Usuario();
	
	public void gravar(){
		Cargo cargo = new DAO<Cargo>(Cargo.class).buscarPorId(this.id_cargo);
		Grupo grupo = new DAO<Grupo>(Grupo.class).buscarPorId(this.id_grupo);
		funcionario.setCargo(cargo);
		usuario.setGrupo(grupo);
		funcionario.setUsuario(usuario);
		DAO<Funcionario> dao = new DAO<Funcionario>(Funcionario.class);
		if (!funcionario.getUsuario().getSenha().equals(senha_bd)){
			funcionario.getUsuario().setSenha(Utils.toMD5(funcionario.getUsuario().getSenha()));
		}
		try {
			if(funcionario.getUsuario().getId() == null){
				new DAO<Usuario>(Usuario.class).adicionar(usuario);
			}
			else {
				new DAO<Usuario>(Usuario.class).atualizar(usuario);
			}
			if(funcionario.getId() == null) {
				dao.adicionar(funcionario);
			} else {
				dao.atualizar(funcionario);
			}
			funcionario = new Funcionario();
			usuario = new Usuario();
			id_grupo = null;
			id_cargo = null;
		} catch(Exception e){
			FacesContext.getCurrentInstance().addMessage("funcionario", new FacesMessage("CPF e/ou Login já existem."));
			e.printStackTrace();
		}
	}
	
	public void carregar(Funcionario funcionario){
		this.funcionario = funcionario;
		this.id_cargo = funcionario.getCargo().getId();
		this.id_grupo = funcionario.getUsuario().getGrupo().getId();
		this.usuario = funcionario.getUsuario();
		funcionario.getUsuario().setSenha(null);
		this.senha_bd = funcionario.getUsuario().getSenha();
	}
	
	public void remover(Funcionario funcionario){
		try {
			Usuario usuario_remover = funcionario.getUsuario();
			new DAO<Funcionario>(Funcionario.class).remover(funcionario);
			new DAO<Usuario>(Usuario.class).remover(usuario_remover);
			FacesContext.getCurrentInstance().addMessage("funcionario", new FacesMessage("Funcionário removido com sucesso"));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("funcionario", new FacesMessage("Funcionário não pode ser removido, está associado a uma venda."));
		}
	}
	
	public String formFuncionario(){
		return "funcionario?faces-redirect=true";
	}
	
	// getters e setters
	public Integer getId_cargo() {
		return id_cargo;
	}
	
	public void setId_cargo(Integer id_cargo) {
		this.id_cargo = id_cargo;
	}
	
	public Integer getId_grupo() {
		return id_grupo;
	}
	
	public void setId_grupo(Integer id_grupo) {
		this.id_grupo = id_grupo;
	}
	
	public List<Genero> getGeneros(){
		return Arrays.asList(Genero.values());
	}
	
	public List<Grupo> getGrupos(){
		List<Grupo> lista = new DAO<Grupo>(Grupo.class).buscarTodos();
		List<Grupo> lista1 = new LinkedList<Grupo>();
		
		for(Grupo g : lista){
			if(g.getId() != 3){
				lista1.add(g);
			}
		}
	
		return lista1;
	}
	
	public List<Cargo> getTodosCargos(){
		return new DAO<Cargo>(Cargo.class).buscarTodos();
	}

	public String getSenha_bd() {
		return senha_bd;
	}

	public void setSenha_bd(String senha_bd) {
		this.senha_bd = senha_bd;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public List<Funcionario> getTodosFuncionarios(){
		return new FuncionarioDao().listaFuncionarios();
	}
}
