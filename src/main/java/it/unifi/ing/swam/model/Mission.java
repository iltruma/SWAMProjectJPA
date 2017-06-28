package it.unifi.ing.swam.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "missions")
public class Mission extends BaseEntity {

    @OneToMany
    private List<WayBill> waybills;

    public Mission() {
    }

    public Mission(String uuid) {
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
