package it.unifi.ing.swam.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "missions")
public class Mission extends BaseEntity {

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="mission_id")
    private List<Waybill> waybills;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Calendar date;

    @ManyToOne
    private Driver driver;

    protected Mission() {

    }

    public Mission(String uuid) {
        super(uuid);
        waybills = new ArrayList<>();
    }

    public List<Waybill> getWaybills() {
        return waybills;
    }

    public void addWaybill(Waybill w) {
        waybills.add(w);
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

}
