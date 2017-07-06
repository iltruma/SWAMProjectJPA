package it.unifi.ing.swam.dao;

import it.unifi.ing.swam.model.Fare;

public class FareDao extends BaseDao {

    public Fare findById(Long id) {
        return entityManager.find(Fare.class, id);
    }

}
