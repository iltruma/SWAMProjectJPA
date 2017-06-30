package it.unifi.ing.swam.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Embeddable
public class Load {

    private Float totalVolume;
    private Float totalWeight;
    @OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST})
    @JoinColumn(name="waybill_id")
    private List<Item> items;

    public Load() {
        totalVolume = Float.valueOf(0F);
        totalWeight = Float.valueOf(0F);
        this.items = new ArrayList<>();
    }

    public Float getTotalVolume() {
        return totalVolume;
    }

    public Float getTotalWeight() {
        return totalWeight;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item i) {
        this.items.add(i);
        this.totalVolume += i.getVolume();
        this.totalWeight += i.getWeight();
    }

    public Integer getNumItems() {
        return items.size();
    }

}
