package it.unifi.ing.swam.dao;

import it.unifi.ing.swam.model.Operator;

public class OperatorDao extends BaseDao {

    public Operator findById(Long id) {
        return entityManager.find(Operator.class, id);
    }

}
