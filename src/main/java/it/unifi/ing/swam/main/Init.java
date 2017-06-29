package it.unifi.ing.swam.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;

public class Init {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("swamproject");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        for (int i = 0; i < 4; i++) {
            User user = ModelFactory.generateUser();
            user.setUsername("username_" + i);
            user.setPassword("password_" + i);
            user.setName("name_" + i);
            user.setPhone("phone_" + i);
            user.setEmail("email_" + i);
            user.addRoles(ModelFactory.generateOperator());

            em.persist(user);
        }

        em.getTransaction().commit();

        em.close();
        emf.close();
    }

}
