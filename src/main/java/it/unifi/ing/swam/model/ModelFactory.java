package it.unifi.ing.swam.model;

import java.util.UUID;

public class ModelFactory {

    private ModelFactory() {
    }

    public static User generateUser() {
        return new User(UUID.randomUUID().toString());
    }

    public static Operator generateOperator() {
        return new Operator(UUID.randomUUID().toString());
    }

    public static Driver generateDriver() {
        return new Driver(UUID.randomUUID().toString());
    }

    public static Customer generateCustomer() {
        Customer customer = new Customer(UUID.randomUUID().toString());
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
        waybill.setLoad(new Load());
        return waybill;
    }

    public static Item generateItem() {
        return new Item(UUID.randomUUID().toString());
    }

    public static Mission generateMission() {
        return new Mission(UUID.randomUUID().toString());
    }

}
