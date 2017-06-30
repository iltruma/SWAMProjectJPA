package it.unifi.ing.swam.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Item extends BaseEntity {

    private Float weight;
    private Float volume;

    public Item() {
        weight = Float.valueOf(0F);
        volume = Float.valueOf(0F);
    }

    public Item(String uuid) {
        super(uuid);
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeigth(Float weight) {
        this.weight = weight;
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

}
