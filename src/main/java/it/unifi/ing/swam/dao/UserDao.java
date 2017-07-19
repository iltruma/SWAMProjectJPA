package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.User;

public class UserDao extends BaseDao<User> {

    private static final long serialVersionUID = 24L;

    public UserDao() {
        super(User.class);
    }

    public void save(User user) {
        if(user.getRoles().isEmpty())
            throw new IllegalArgumentException("You cannot save this entity");
        super.save(user);
    }

    public User findByEmail(String email) {
        List<User> result = entityManager.createQuery("FROM User WHERE email = :email", User.class)
                .setParameter("email", email).getResultList();
        if (!result.isEmpty())
            return result.get(0);
        return null;
    }

    public List<User> findByName(String name) {
        return entityManager.createQuery("FROM User WHERE name = :name", User.class).setParameter("name", name)
                .getResultList();
    }

    public User findByPhone(String phone) {
        List<User> result = entityManager.createQuery("FROM User WHERE phone = :phone", User.class)
                .setParameter("phone", phone).getResultList();
        if (!result.isEmpty())
            return result.get(0);
        return null;
    }

    public User findByUsername(String username) {
        List<User> result = entityManager.createQuery("FROM User WHERE username = :username", User.class)
                .setParameter("username", username).getResultList();
        if (!result.isEmpty())
            return result.get(0);
        return null;
    }

    public List<User> findByAgencyId(Long agencyId) {
        return entityManager.createQuery("FROM User WHERE agency_id = :agency_id", User.class)
                .setParameter("agency_id", agencyId).getResultList();
    }

    public User findByLoginInfo(User user) {
        List<User> result = entityManager
                .createQuery("FROM User WHERE username = :username AND password = :pass", User.class)
                .setParameter("username", user.getUsername()).setParameter("pass", user.getPassword()).setMaxResults(1)
                .getResultList();
        if (!result.isEmpty())
            return result.get(0);
        return null;
    }

    public byte[] findSaltByUsername(String username) {
        List<User> result = entityManager
                .createQuery("FROM User WHERE username = :username", User.class)
                .setParameter("username", username).setMaxResults(1)
                .getResultList();
        if (!result.isEmpty())
            return result.get(0).getSalt();
        return null;
    }
}
