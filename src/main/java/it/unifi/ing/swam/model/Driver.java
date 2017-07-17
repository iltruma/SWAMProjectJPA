package it.unifi.ing.swam.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "drivers")
public class Driver extends Role {

    private String zone;
    @Embedded
    private Truck truck;
    @OneToMany(cascade = { CascadeType.PERSIST }, fetch=FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private List<Mission> missions;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AvailType availType;

    public enum AvailType {
        AVAILABLE, UNAVAILABLE
    }

    protected Driver() {
        type = Role.Type.DRIVER;
    }

    public Driver(String uuid) {
        super(uuid, Role.Type.DRIVER);
        missions = new ArrayList<>();
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
        missions.add(m);
    }

    public AvailType isAvailable() {
        return availType;
    }

    public void setAvailType(AvailType availType) {
        this.availType = availType;
    }

}
