package farmacia.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	// Carregar configuração do persistence.xml
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("farmacia");
	
	public static EntityManager getEntityManager(){
		// Conecta com o banco
		return emf.createEntityManager();
	}
	
	
}
