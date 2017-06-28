package it.unifi.ing.swam.model;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer extends Role {

    public enum State {
        ACTIVE, BLOCKED
    }

    @Enumerated(EnumType.STRING)
    private State state;
    @Embedded
    private Address address;
    @OneToMany
    private List<Fare> fares;
    @OneToMany(mappedBy = "sender")
    private List<WayBill> waybills;
    @ManyToOne
    private Operator operator;

    protected Customer() {
    }

    public Customer(String uuid) {
        super(uuid);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Fare> getFares() {
        return fares;
    }

    public void addFare(Fare f) {
        this.fares.add(f);
    }

    public List<WayBill> getWaybills() {
        return waybills;
    }

    public void addWaybill(WayBill w) {
        this.waybills.add(w);
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
