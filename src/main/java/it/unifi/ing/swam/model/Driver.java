package it.unifi.ing.swam.model;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="drivers")
public class Driver extends BaseEntity implements Role {
	
	private String zone;
	@Embedded private Truck truck;
	@OneToMany private List <Mission> missions;
	
	protected Driver(){}
	
	public Driver(String uuid) {
		super(uuid);
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
	
}
