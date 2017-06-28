package it.unifi.ing.swam.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="fares")
public class Fare extends BaseEntity {
	
	protected Fare(){}
	
	public Fare(String uuid) {
		super(uuid);
	}
	
}
