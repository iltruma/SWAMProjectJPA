package it.unifi.ing.swam.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Truck {

    private String model;
    private Float volume;
    private Float capacity;
    private Integer registrationYear;
    private String brand;
    @Enumerated(EnumType.STRING)
    private TruckType type;

    public Truck() {

    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public Float getCapacity() {
        return capacity;
    }

    public void setCapacity(Float capacity) {
        this.capacity = capacity;
    }

    public Integer getRegistrationYear() {
        return registrationYear;
    }

    public void setRegistrationYear(Integer registrationYear) {
        this.registrationYear = registrationYear;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public TruckType getType() {
        return type;
    }

    public void setType(TruckType type) {
        this.type = type;
    }

}
