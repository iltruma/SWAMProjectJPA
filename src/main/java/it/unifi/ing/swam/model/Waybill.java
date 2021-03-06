package it.unifi.ing.swam.model;

import java.util.Calendar;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "waybills")
public class Waybill extends BaseEntity {

	@ManyToOne
	@JoinColumn(name="operator_id")
	private User operator; //Operator

	@NotNull
	@Embedded
	private Receiver receiver;

	@NotNull
	@ManyToOne
	@JoinColumn(name="sender_id")
	private User sender; //Customer

	@NotNull
	@Embedded
	private Load load;

	@Temporal(TemporalType.DATE)
	private Calendar acceptDate;

	@Temporal(TemporalType.DATE)
	private Calendar deliveryDate;

	@NotNull
	private Float cost;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Tracking tracking;

	private Boolean signed;

	protected Waybill() {

	}

	public Waybill(String uuid) {
		super(uuid);
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) throws IllegalArgumentException {
		if(!operator.hasRole(Role.Type.OPERATOR))
			throw new IllegalArgumentException("operator has not the Operator role");
		this.operator = operator;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public User getSender() {
		return sender;
	}

	public void setSender (User sender) throws IllegalArgumentException {
		if(!sender.hasRole(Role.Type.CUSTOMER))
			throw new IllegalArgumentException("sender has not the Customer role");
		this.sender = sender;
	}

	public Load getLoad() {
		return load;
	}

	public void setLoad(Load load) {
		this.load = load;
	}

	public Calendar getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Calendar acceptDate) {
		this.acceptDate = acceptDate;
	}

	public Calendar getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Calendar deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Float getCost() {
		if(cost==null)
			retreiveCost();
		return cost;
	}

	public Tracking getTracking() {
		return tracking;
	}

	public void setTracking(Tracking tracking) {
		this.tracking = tracking;
	}

	public Float getWeight() {
		return load.getTotalWeight();
	}

	public Float getVolume() {
		return load.getTotalVolume();
	}

	public Integer getNumItems() {
		return load.getNumItems();
	}

	public Boolean isSigned() {
		return signed;
	}

	public void setSign(Boolean signed) {
		this.signed = signed;
	}

	public void retreiveCost(){
		Float r = this.getSender().getCustomerRole().getFares().get(0).getFunctionCost();
		cost = (this.getLoad().getTotalVolume()+this.getLoad().getTotalWeight())*r/4.0F;
	}
}
