package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.Operator;
import it.unifi.ing.swam.model.User;

public class OperatorDao extends BaseDao {

    private static final long serialVersionUID = 22L;

    public Operator findById(Long id) {
        return entityManager.find(Operator.class, id);
    }

    @Deprecated
    public Operator findByUserId(Long ownerId) {
        List<Operator> result = entityManager.createQuery("FROM Operator WHERE owner_id = :owner_id", Operator.class)
                .setParameter("owner_id", ownerId).getResultList();
        if (!result.isEmpty())
            return result.get(0);
        return null;
    }

    public Operator findByUser(User owner) {
        List<Operator> result = entityManager
                .createQuery("SELECT o FROM Operator o WHERE o.owner = :owner", Operator.class)
                .setParameter("owner", owner).getResultList();
        if (!result.isEmpty())
            return result.get(0);
        return null;
    }

}
