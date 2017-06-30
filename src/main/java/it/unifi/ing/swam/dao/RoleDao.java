package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.Role;

public class RoleDao extends BaseDao {

    public List<Role> findByUserId(Long ownerId) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.owner_id = :owner_id", Role.class)
                .setParameter("owner_id", ownerId).getResultList();
    }

}
