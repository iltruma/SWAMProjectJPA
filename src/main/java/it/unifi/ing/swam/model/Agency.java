package it.unifi.ing.swam.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="agencies")
public class Agency extends BaseEntity {

	private Agency(){}

	public Agency(String uuid) {
		super(uuid);
	}

}
