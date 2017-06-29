package it.unifi.ing.swam.main;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.Role;
import it.unifi.ing.swam.model.User;

public class Main {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("swamproject");
		EntityManager em = emf.createEntityManager();

		User user = ModelFactory.generateUser();
		user.setUsername("username");
		user.setPassword("password");

		Role operator = ModelFactory.generateOperator();
		user.addRoles(operator);

		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();

		em.close();
		emf.close();


	}

}
