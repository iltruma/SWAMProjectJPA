package it.unifi.ing.swam.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainCreator {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("swamproject");
		EntityManager em = emf.createEntityManager();
		
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		User user2  = em.find(User.class, 1L);
		em.getTransaction().commit();
		
		System.out.println(user2.getUsername());
		System.out.println(user2.getPassword());
		
		em.close();
		emf.close();
		
		
	}

}
