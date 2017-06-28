package it.unifi.ing.swam.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="fares")
public class Fare extends BaseEntity {
	
	@Temporal(TemporalType.DATE) private Date startDate;
	@Temporal(TemporalType.DATE) private Date endDate;
	private String zone;
	private String functionCost;

	protected Fare(){}
	
	public Fare(String uuid) {
		super(uuid);
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getFunctionCost() {
		return functionCost;
	}

	public void setFunctionCost(String functionCost) {
		this.functionCost = functionCost;
	}
	
}
