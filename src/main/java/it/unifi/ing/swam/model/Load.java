package it.unifi.ing.swam.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Embeddable
public class Load {

    private Float totalVolume;
    private Float totalWeight;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="waybill_id")
    private List<Item> items;

    public Load() {
        totalVolume = Float.valueOf(0F);
        totalWeight = Float.valueOf(0F);
        items = new ArrayList<>();
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
        items.add(i);
        totalVolume += i.getVolume();
        totalWeight += i.getWeight();
    }

    public void removeItem(Item i) {
        items.remove(i);
        totalVolume -= i.getVolume();
        totalWeight -= i.getWeight();
    }

    public Integer getNumItems() {
        return items.size();
    }

}
