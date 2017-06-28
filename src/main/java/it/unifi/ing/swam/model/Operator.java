package it.unifi.ing.swam.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="operators")
public class Operator extends BaseEntity implements Role {
	
	protected Operator(){}
	
	public Operator(String uuid) {
		super(uuid);
	}
	
}
