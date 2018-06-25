package farmacia.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import farmacia.modelo.Funcionario;
import farmacia.utils.JPAUtil;

public class FuncionarioDao {
	public List<Funcionario> listaFuncionarios(){
		String jpql = "SELECT f FROM Funcionario f JOIN FETCH f.cargo JOIN FETCH f.usuario";
		EntityManager em = JPAUtil.getEntityManager();	
		Query query = em.createQuery(jpql);	
		List<Funcionario> lista = query.getResultList();	
		em.close();
		
		return lista;
	}
}
