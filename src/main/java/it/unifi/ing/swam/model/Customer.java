package it.unifi.ing.swam.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="customers")
public class Customer extends BaseEntity implements Role {

	@OneToMany
	private List<Fare> fares;

	protected Customer(){}

	public Customer(String uuid) {
		super(uuid);
	}

}
