package it.unifi.ing.swam.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Address {

    @NotNull
    private String street;

    @NotNull
    private String city;

    @NotNull
    @Column(name = "address_state")
    private String state;

    @NotNull
    private String zip;

    public Address() {
        street = "";
        city = "";
        state = "";
        zip = "";
    }

    public Address(String street, String city, String state, String zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String format() {
        String address = street + " " + city + ", " + state + " " + zip;

        if (address.endsWith(",  "))
            address = address.substring(0, address.length() - 3);
        else if (address.startsWith(" , "))
            address = address.substring(3, address.length());

        return address;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

}
