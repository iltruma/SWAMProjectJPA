package it.unifi.ing.swam.dao;

import it.unifi.ing.swam.model.Operator;
import it.unifi.ing.swam.model.User;

public class OperatorDao extends BaseDao {

    public Operator findById(Long id) {
        return entityManager.find(Operator.class, id);
    }

    @Deprecated
    public Operator findByUserId(Long ownerId) {
        return entityManager.createQuery("FROM Operator WHERE owner_id = :owner_id", Operator.class)
                .setParameter("owner_id", ownerId).getSingleResult();
    }

    public Operator findByUser(User owner) {
        return entityManager.createQuery("SELECT o FROM Operator o WHERE o.owner = :owner", Operator.class)
                .setParameter("owner", owner).getSingleResult();
    }

}
