package it.unifi.ing.swam.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "drivers")
public class Driver extends Role {

    private String zone;
    @Embedded
    private Truck truck;
    @OneToMany(cascade={CascadeType.PERSIST}, fetch=FetchType.LAZY)
    @JoinColumn(name="driver_id")
    private List<Mission> missions;

    protected Driver() {

    }

    public Driver(String uuid) {
        super(uuid);
        this.type = RoleType.DRIVER;
        this.missions = new ArrayList<>();
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }

    public void addMission(Mission m) {
        this.missions.add(m);
    }

}
