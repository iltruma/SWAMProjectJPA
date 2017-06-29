package it.unifi.ing.swam.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Embeddable
public class Load {

    private Float totalVolume;
    private Float totalWeight;
    @OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST})
    private List<Package> packages;

    Load() {
        this.packages = new ArrayList<>();
    }

    public Float getTotalVolume() {
        return totalVolume;
    }

    public Float getTotalWeight() {
        return totalWeight;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void addPackage(Package p) {
        this.packages.add(p);
        this.totalVolume += p.getVolume();
        this.totalWeight += p.getWeigth();
    }

    public Integer getNumItems() {
        return packages.size();
    }

}
