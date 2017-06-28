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

    private String name;
    @Enumerated(EnumType.STRING)
    private State state;
    @Embedded
    private Address address;
    private String phone;
    @OneToMany
    private List<Fare> fares;
    @OneToMany(mappedBy = "sender")
    private List<Fare> waybills;
    @ManyToOne
    private Operator operator;

    protected Customer() {
    }

    public Customer(String uuid) {
        super(uuid);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Fare> getFares() {
        return fares;
    }

    public void setFares(List<Fare> fares) {
        this.fares = fares;
    }

    public List<Fare> getWaybills() {
        return waybills;
    }

    public void setWaybills(List<Fare> waybills) {
        this.waybills = waybills;
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
