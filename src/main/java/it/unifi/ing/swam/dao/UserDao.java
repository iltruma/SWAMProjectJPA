package it.unifi.ing.swam.dao;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import it.unifi.ing.swam.model.User;

public class UserDao extends BaseDao {

    public UserDao() {
        // FIXME - Codice da togliere!
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("swamproject");
        entityManager = emf.createEntityManager();
    }

    public List<User> advancedSearch(String username, String name, String phone, String email) {

        String query = "FROM User u WHERE ";

        query += "u.username = :username ";

        query += "OR u.name = :name ";

        query += "OR u.phone = :phone ";

        query += "OR u.email = :email";


        return entityManager.createQuery(query, User.class).setParameter("username", username)
                .setParameter("name", name).setParameter("phone", phone).setParameter("email", email)
                .getResultList();
    }

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

}
