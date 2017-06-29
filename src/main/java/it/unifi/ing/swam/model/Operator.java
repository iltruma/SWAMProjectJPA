package it.unifi.ing.swam.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "operators")
public class Operator extends Role {

    @OneToMany(mappedBy = "operator", fetch=FetchType.LAZY)
    private List<WayBill> waybills;

    protected Operator() {
    }

    public Operator(String uuid) {
        super(uuid);
        this.waybills = new ArrayList<>();
    }

    public List<WayBill> getWaybills() {
        return waybills;
    }

    public void addWaybill(WayBill w) {
        this.waybills.add(w);
    }

}
