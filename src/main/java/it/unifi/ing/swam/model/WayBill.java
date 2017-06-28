package it.unifi.ing.swam.model;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "waybills")
public class WayBill extends BaseEntity {

    @ManyToOne
    private Operator operator;
    @Embedded
    private Receiver receiver;
    @ManyToOne
    private Customer sender;
    @ManyToOne
    private Agency departureAgency;
    @ManyToOne
    private Agency destinationAgency;
    @Embedded
    private Load load;
    @Temporal(TemporalType.DATE)
    private Date acceptDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;
    private Float cost;

    private enum tracking {
        SHIPPING, DELIVERING, DELIVERED
    };

    public WayBill() {

    }

    public WayBill(String uuid) {
        super(uuid);
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Agency getDepartureAgency() {
        return departureAgency;
    }

    public void setDepartureAgency(Agency departureAgency) {
        this.departureAgency = departureAgency;
    }

    public Agency getDestinationAgency() {
        return destinationAgency;
    }

    public void setDestinationAgency(Agency destinationAgency) {
        this.destinationAgency = destinationAgency;
    }

    public Load getLoad() {
        return load;
    }

    public void setLoad(Load load) {
        this.load = load;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

}
