package it.unifi.ing.swam.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="missions")
public class Mission extends BaseEntity {

	@OneToMany private List<Fare> waybills;
	
	
	public Mission(){
	}
	
	public Mission(String uuid) {
		super(uuid);
	}

	public List<Fare> getWaybills() {
		return waybills;
	}

	public void setWaybills(List<Fare> waybills) {
		this.waybills = waybills;
	}
	
}
