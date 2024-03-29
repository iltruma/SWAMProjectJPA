package it.unifi.ing.swam.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Embeddable
public class Receiver {

    private Address address;
    private String name;
    private String phone;
    private String email;

    @NotNull
    @ManyToOne
    private Agency destinationAgency;

    public Receiver() {

    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Agency getDestinationAgency() {
        return destinationAgency;
    }

    public void setDestinationAgency(Agency destinationAgency) {
        this.destinationAgency = destinationAgency;
    };

}
