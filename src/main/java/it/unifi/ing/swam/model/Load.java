package it.unifi.ing.swam.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Embeddable
public class Load {

	private float totalVolume;
	private float totalWeight;
	@OneToMany private List<Package> packages;
	
	public float getTotalVolume() {
		return totalVolume;
	}
	public void setTotalVolume(float totalVolume) {
		this.totalVolume = totalVolume;
	}
	public float getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(float totalWeight) {
		this.totalWeight = totalWeight;
	}
	public List<Package> getPackages() {
		return packages;
	}
	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}
	
}
