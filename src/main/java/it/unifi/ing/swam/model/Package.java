package it.unifi.ing.swam.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "packages")
public class Package extends BaseEntity {

    private Float weigth;
    private Float volume;

    public Package() {

    }

    public Package(String uuid) {
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
