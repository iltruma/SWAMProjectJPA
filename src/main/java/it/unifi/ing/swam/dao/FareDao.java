package it.unifi.ing.swam.dao;

import it.unifi.ing.swam.model.Fare;

public class FareDao extends BaseDao {

    private static final long serialVersionUID = 19L;

    public Fare findById(Long id) {
        return entityManager.find(Fare.class, id);
    }

}
