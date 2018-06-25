package farmacia.dao;

import javax.persistence.EntityManager;
import farmacia.modelo.Produto;
import farmacia.utils.JPAUtil;

public class ProdutoDao {
	
	public void atualizaEstoque(Produto produto, int qtde, EntityManager em){
		produto = em.find(Produto.class, produto.getId());				
		produto.setEstoque(produto.getEstoque() + qtde);
	}
	
	public void atualizaEstoque(Produto produto, int qtde){
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		
		produto = em.find(Produto.class, produto.getId());				
		produto.setEstoque(produto.getEstoque() + qtde);
		
		em.getTransaction().commit();
		
		em.close();
	}

}
