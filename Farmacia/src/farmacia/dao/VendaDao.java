package farmacia.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import farmacia.modelo.Venda;
import farmacia.utils.JPAUtil;

public class VendaDao {
	public List<Venda> listaVendas(){
		String jpql = "SELECT v FROM Venda v JOIN FETCH v.cliente JOIN FETCH v.funcionario";
		EntityManager em = JPAUtil.getEntityManager();	
		Query query = em.createQuery(jpql);	
		List<Venda> lista = query.getResultList();	
		em.close();
		
		return lista;
	}
}
