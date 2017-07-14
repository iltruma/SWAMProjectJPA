package it.unifi.ing.swam.dao;

import it.unifi.ing.swam.model.Fare;

public class FareDao extends BaseDao<Fare> {

    private static final long serialVersionUID = 19L;

    public FareDao() {
        super(Fare.class);
    }

}
