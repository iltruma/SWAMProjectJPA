package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.Role;
import it.unifi.ing.swam.model.User;

public class RoleDao extends BaseDao<Role> {

    private static final long serialVersionUID = 23L;

    public RoleDao() {
        super(Role.class);
    }

    @Deprecated
    public List<Role> findByUserId(Long ownerId) {
        return entityManager.createQuery("FROM Role WHERE owner_id = :owner_id", Role.class)
                .setParameter("owner_id", ownerId).getResultList();
    }

    public List<Role> findByUser(User owner) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.owner = :owner", Role.class)
                .setParameter("owner", owner).getResultList();
    }

}
