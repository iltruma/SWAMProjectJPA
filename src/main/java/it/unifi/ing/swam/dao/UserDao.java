package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.User;

public class UserDao extends BaseDao {

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    public User findByEmail(String email) {
        return entityManager.createQuery("FROM User u WHERE" + "u.email = :email", User.class)
                .setParameter("email", email).getSingleResult();
    }

    public List<User> findByName(String name) {
        return entityManager.createQuery("FROM User u WHERE" + "u.name = :name", User.class).setParameter("name", name)
                .getResultList();
    }

    public User findByPhone(String phone) {
        return entityManager.createQuery("FROM User u WHERE" + "u.phone = :phone", User.class)
                .setParameter("phone", phone).getSingleResult();
    }

    public User findByUsername(String username) {
        return entityManager.createQuery("FROM User u WHERE" + "u.username = :username", User.class)
                .setParameter("username", username).getSingleResult();
    }

    public List<User> findByAgencyId(Long agencyId) {
        return entityManager.createQuery("FROM User u WHERE" + "u.agency_id = :agency_id", User.class)
                .setParameter("agency_id", agencyId).getResultList();
    }

}
