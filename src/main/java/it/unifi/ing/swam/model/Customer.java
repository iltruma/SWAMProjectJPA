package it.unifi.ing.swam.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer extends Role {

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_state")
    private State state;
    @Embedded
    private Address address;
    @OneToMany(cascade={CascadeType.PERSIST}, fetch=FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private List<Fare> fares;
    @ManyToOne
    private User operator; //Operator


    protected Customer() {
    }

    public Customer(String uuid) {
        super(uuid);
        this.fares = new ArrayList<>();
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

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
