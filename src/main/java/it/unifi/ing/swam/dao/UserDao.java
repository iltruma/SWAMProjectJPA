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

        String query = "from swamproject.users where ";

        query += "(:uusername IS NULL OR users.username = :uusername) ";

        query += "AND (:uname IS NULL OR users.name = :uname) ";

        query += "AND (:uphone IS NULL OR users.phone = :uphone) ";

        query += "AND (:uemail IS NULL OR users.email = :uemail)";

        return entityManager.createQuery(query, User.class).setParameter("uusername", username)
                .setParameter("uname", name).setParameter("uphone", phone).setParameter("uemail", email)
                .getResultList();
    }

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

}
