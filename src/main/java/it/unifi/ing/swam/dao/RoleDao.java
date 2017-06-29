package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.Role;

public class RoleDao extends BaseDao {

    public List<Role> findByUserId(Long userId) {
        return entityManager.createQuery("FROM Role r WHERE" + "r.user_id = :user_id", Role.class)
                .setParameter("user_id", userId).getResultList();
    }

}
