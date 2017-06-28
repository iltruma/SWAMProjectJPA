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
        return new Customer(UUID.randomUUID().toString());
    }

    public static Fare generateFare() {
        return new Fare(UUID.randomUUID().toString());
    }

    public static Agency generateAgency() {
        return new Agency(UUID.randomUUID().toString());
    }

    public static WayBill generateWayBill() {
        return new WayBill(UUID.randomUUID().toString());
    }

    public static Package generatePackage() {
        return new Package(UUID.randomUUID().toString());
    }

    public static Mission generateMission() {
        return new Mission(UUID.randomUUID().toString());
    }

}
