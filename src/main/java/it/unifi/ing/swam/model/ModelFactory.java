package it.unifi.ing.swam.model;

import java.util.UUID;


public class ModelFactory {

    private ModelFactory() {
    }

    public static User generateUser() {
        return new User(UUID.randomUUID().toString());
    }

    public static User generateUser(String username, String password) {
        User u =  new User(UUID.randomUUID().toString());
        u.setPassword(password);
        u.setUsername(username);
        return u;
    }

    public static Operator generateOperator() {
        return new Operator(UUID.randomUUID().toString());
    }

    public static Driver generateDriver() {
        Driver driver = new Driver(UUID.randomUUID().toString());
        driver.setTruck(new Truck());
        driver.setAvailType(Driver.AvailType.AVAILABLE);
        return driver;
    }

    public static Customer generateCustomer() {
        Customer customer = new Customer(UUID.randomUUID().toString());
        customer.setAddress(new Address());
        customer.setState(State.ACTIVE);
        return customer;
    }

    public static Fare generateFare() {
        return new Fare(UUID.randomUUID().toString());
    }

    public static Agency generateAgency() {
        return new Agency(UUID.randomUUID().toString());
    }

    public static Waybill generateWaybill() {
        Waybill waybill = new Waybill(UUID.randomUUID().toString());
        waybill.setTracking(Tracking.IDLE);
        waybill.setReceiver(new Receiver());
        waybill.getReceiver().setAddress(new Address());
        waybill.setLoad(new Load());
        waybill.setCost(0F);
        return waybill;
    }

    public static Item generateItem() {
        return new Item(UUID.randomUUID().toString());
    }

    public static Mission generateMission() {
        return new Mission(UUID.randomUUID().toString());
    }

}
