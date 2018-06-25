package farmacia.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import farmacia.utils.JPAUtil;

public class DAO <T> {
	private final Class<T> classe;
	
	public DAO(Class<T> classe) {
		this.classe = classe;
	}
	
	public void adicionar(T t){
		EntityManager em = JPAUtil.getEntityManager();
		
		em.getTransaction().begin();
		
		em.persist(t);
		
		em.getTransaction().commit();
		
		em.close();
	}
	
	public void adicionar(T t, EntityManager em){
		em.persist(t);
	}
	
	public void remover(T t) {
		EntityManager em = JPAUtil.getEntityManager();
		
		em.getTransaction().begin();
		
		t = em.merge(t);
		em.remove(t);
		
		em.getTransaction().commit();
		
		em.close();
	}
	
	public void remover(T t, EntityManager em) {
		t = em.merge(t);
		em.remove(t);
	}
	
	public void atualizar(T t) {
		EntityManager em = JPAUtil.getEntityManager();
		
		em.getTransaction().begin();
		
		em.merge(t);
		
		em.getTransaction().commit();
		
		em.close();
	}
	
	public void atualizar(T t, EntityManager em) {
		em.merge(t);
	}
	
	public List<T> buscarTodos(){
		String jpql = "SELECT t FROM " + this.classe.getName()
				+ " t";
		
		EntityManager em = JPAUtil.getEntityManager();
		
		Query query = em.createQuery(jpql);
		
		List<T> lista = query.getResultList();
		
		em.close();
		
		return lista;
	}
	
	public T buscarPorId(int id){
		EntityManager em = JPAUtil.getEntityManager();
		
		T t = em.find(this.classe, id);
		
		em.close();
		
		return t;
	}
	
	public T buscarPorId(int id, EntityManager em){
		T t = em.find(this.classe, id);
		return t;
	}
	
	public Long contarTodos(){
		String jpql = "SELECT count(t) FROM " + 
				this.classe.getName() +" t";
		
		EntityManager em = JPAUtil.getEntityManager();
		
		Query query = em.createQuery(jpql);
		
		Long qtde = (Long)query.getSingleResult();
		
		em.close();
		
		return qtde;
	}

}
