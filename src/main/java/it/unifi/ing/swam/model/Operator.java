package it.unifi.ing.swam.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "operators")
public class Operator extends Role {

    @OneToMany(mappedBy = "operator")
    private List<WayBill> waybills;

    protected Operator() {
    }

    public Operator(String uuid) {
        super(uuid);
    }

}
