package farmacia.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import farmacia.modelo.ProdutoVenda;
import farmacia.utils.JPAUtil;

public class ProdutoVendaControllerDao {
	public List<ProdutoVenda> listaProdutoVenda(){
		String jpql = "SELECT p FROM ProdutoVenda p JOIN FETCH p.produto JOIN FETCH p.venda";
		EntityManager em = JPAUtil.getEntityManager();	
		Query query = em.createQuery(jpql);	
		List<ProdutoVenda> lista = query.getResultList();	
		em.close();
		
		return lista;
	}
}
