package it.unifi.ing.swam.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Item extends BaseEntity {

    private Float weigth;
    private Float volume;

    public Item() {

    }

    public Item(String uuid) {
        super(uuid);
    }

    public Float getWeigth() {
        return weigth;
    }

    public void setWeigth(Float weigth) {
        this.weigth = weigth;
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

}
