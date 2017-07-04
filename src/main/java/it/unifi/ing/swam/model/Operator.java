package it.unifi.ing.swam.model;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "operators")
public class Operator extends Role {

    protected Operator() {

    }

    public Operator(String uuid) {
        super(uuid);
        this.type = Role.Type.OPERATOR;
    }

}
