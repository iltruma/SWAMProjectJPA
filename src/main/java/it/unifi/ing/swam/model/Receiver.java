package it.unifi.ing.swam.model;

import javax.persistence.Embeddable;

@Embeddable
public class Receiver {

    private Address address;
    private String name;
    private String phone;

    private enum sign {
        UNSIGN, SIGN
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
    };

}
