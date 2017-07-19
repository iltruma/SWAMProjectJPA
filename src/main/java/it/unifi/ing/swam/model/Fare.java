package it.unifi.ing.swam.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "fares")
public class Fare extends BaseEntity {

	@Temporal(TemporalType.DATE)
	private Calendar startDate;
	@Temporal(TemporalType.DATE)
	private Calendar endDate;
	private String zone;
	private Float functionCost;

	protected Fare() {

	}

	public Fare(String uuid) {
		super(uuid);
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public Float getFunctionCost() {
		return functionCost;
	}

	public void setFunctionCost(Float functionCost) {
		this.functionCost = functionCost;
	}

}
