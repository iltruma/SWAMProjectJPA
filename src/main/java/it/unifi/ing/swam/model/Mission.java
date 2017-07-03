package it.unifi.ing.swam.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "missions")
public class Mission extends BaseEntity {

    @OneToMany
    @JoinColumn(name="mission_id")
    private List<Waybill> waybills;
    @Temporal(TemporalType.DATE)
    private Calendar date;

    public Mission() {
    }

    public Mission(String uuid) {
        super(uuid);
        this.waybills = new ArrayList<>();
    }

    public List<Waybill> getWaybills() {
        return waybills;
    }

    public void addWaybill(Waybill w) {
        this.waybills.add(w);
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

}
