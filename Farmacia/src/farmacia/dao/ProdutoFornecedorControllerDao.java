package farmacia.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import farmacia.modelo.ProdutoFornecedor;
import farmacia.utils.JPAUtil;

public class ProdutoFornecedorControllerDao {
	public List<ProdutoFornecedor> listaProdutoFornecedor(){
		String jpql = "SELECT p FROM ProdutoFornecedor p JOIN FETCH p.produto JOIN FETCH p.fornecedor";
		EntityManager em = JPAUtil.getEntityManager();	
		Query query = em.createQuery(jpql);	
		List<ProdutoFornecedor> lista = query.getResultList();	
		em.close();
		
		return lista;
	}
}
