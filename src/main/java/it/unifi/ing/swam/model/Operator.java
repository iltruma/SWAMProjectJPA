package it.unifi.ing.swam.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "operators")
public class Operator extends Role {

    protected Operator() {
        type = Role.Type.OPERATOR;
    }

    public Operator(String uuid) {
        super(uuid, Role.Type.OPERATOR);
    }

}
