package it.unifi.ing.swam.model;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="drivers")
public class Driver extends BaseEntity implements Role {
	
	@Embedded
	private Truck truck;
	
	protected Driver(){}
	
	public Driver(String uuid) {
		super(uuid);
	}
	
}
