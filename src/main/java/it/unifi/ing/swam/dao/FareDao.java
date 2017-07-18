package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.Fare;

public class FareDao extends BaseDao<Fare> {

    private static final long serialVersionUID = 19L;

    public FareDao() {
        super(Fare.class);
    }

    public Boolean isUnassignedToCustomer(Fare fare) {
        List<Fare> result = entityManager
                .createQuery("SELECT f FROM Fare f WHERE f.id = :id AND customer_id IS NULL", Fare.class)
                .setParameter("id", fare.getId()).getResultList();
        if (result.isEmpty())
            return false;
        return true;
    }

}
