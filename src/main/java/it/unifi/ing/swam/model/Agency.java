package it.unifi.ing.swam.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="agencies")
public class Agency extends BaseEntity {
	
	protected Agency(){}
	
	public Agency(String uuid) {
		super(uuid);
	}
	
}
