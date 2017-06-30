package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.Operator;

public class OperatorDao extends BaseDao {

    public Operator findById(Long id) {
        return entityManager.find(Operator.class, id);
    }
    
    public Operator findByUserId(Long ownerId) {
        return entityManager.createQuery("SELECT r FROM Operator r WHERE r.owner_id = :owner_id", Operator.class)
                .setParameter("owner_id", ownerId).getSingleResult();
    }

}
