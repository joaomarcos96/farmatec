package farmacia.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import farmacia.modelo.Usuario;
import farmacia.utils.JPAUtil;

public class UsuarioDao {
	public Usuario buscarPorLoginESenha(Usuario u){
		Usuario resultado = null;
		
		String jpql = "SELECT u FROM Usuario u WHERE u.login = :pLogin AND u.senha = :pSenha";
		
		EntityManager em = JPAUtil.getEntityManager();
		
		TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
		query.setParameter("pLogin", u.getLogin());
		query.setParameter("pSenha", u.getSenha());
		
		try {
			resultado = query.getSingleResult();
		} catch(NoResultException e) {
			e.printStackTrace();
		}
		
		em.close();
		
		
		return resultado;
	}
}
