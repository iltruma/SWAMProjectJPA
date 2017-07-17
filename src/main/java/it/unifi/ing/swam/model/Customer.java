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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "customers")
public class Customer extends Role {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "customer_state")
    private State state;

    @Embedded
    private Address address;

    @OneToMany(cascade = { CascadeType.ALL }, fetch=FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private List<Fare> fares;

    @NotNull
    @ManyToOne
    private User operator; // Operator

    public Customer() {
        type = Role.Type.CUSTOMER;
    }

    public Customer(String uuid) {
        super(uuid, Role.Type.CUSTOMER);
        fares = new ArrayList<>();
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
        fares.add(f);
    }

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) throws IllegalArgumentException {
        if (!operator.hasRole(Role.Type.OPERATOR))
            throw new IllegalArgumentException("operator has not the Operator role");
        this.operator = operator;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Boolean isActive() {
        if (state == State.ACTIVE)
            return true;
        else
            return false;
    }

}
