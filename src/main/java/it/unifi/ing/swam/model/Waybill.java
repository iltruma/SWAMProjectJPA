package it.unifi.ing.swam.model;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "waybills")
public class Waybill extends BaseEntity {

    @ManyToOne
    private User operator; //Operator
    @Embedded
    private Receiver receiver;
    @ManyToOne
    private User sender; //Customer
    @Embedded
    private Load load;
    @Temporal(TemporalType.DATE)
    private Date acceptDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;
    private Float cost;

    private enum Tracking {
        SHIPPING, DELIVERING, DELIVERED
    };

    @Enumerated(EnumType.STRING)
    private Tracking tracking;

    private enum Sign {
        UNSIGN, SIGN
    }

    @Enumerated(EnumType.STRING)
    private Sign sign;

    public Waybill() {

    }

    public Waybill(String uuid) {
        super(uuid);
    }

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
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

    public void setSender(User sender) {
        this.sender = sender;
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

    public Sign getSign() {
        return sign;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }

}
