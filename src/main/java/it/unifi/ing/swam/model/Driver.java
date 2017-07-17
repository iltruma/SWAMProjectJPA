package it.unifi.ing.swam.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "drivers")
public class Driver extends Role {

    private String zone;
    @Embedded
    private Truck truck;

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

    public AvailType isAvailable() {
        return availType;
    }

    public void setAvailType(AvailType availType) {
        this.availType = availType;
    }

}
