package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.Fare;

public class FareDao extends BaseDao<Fare> {

    private static final long serialVersionUID = 19L;

    public FareDao() {
        super(Fare.class);
    }

    public List<Fare> findUnassignedToCustomer() {
        return entityManager.createQuery("FROM Fare WHERE customer_id IS NULL", Fare.class).getResultList();
    }

}
