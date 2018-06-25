package farmacia.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import farmacia.modelo.Grupo;
import farmacia.utils.JPAUtil;

public class GrupoDao {
	
	public Grupo getGrupoFuncionalidades (Grupo grupo) {
		Grupo resultado = null;
		
		String jpql = "SELECT g FROM Grupo g JOIN FETCH g.funcionalidades WHERE g.id = :pIdGrupo";
		
		EntityManager em = JPAUtil.getEntityManager();
		
		TypedQuery<Grupo> query = em.createQuery(jpql, Grupo.class);
		query.setParameter("pIdGrupo", grupo.getId());
		
		resultado = query.getSingleResult();
				
		em.close();
		
		return resultado;
	}

}
