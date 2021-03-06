package it.unifi.ing.swam.model;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class UserTest {

    private static User operatorUser;
    private static User driverUser;
    private static User customerUser;

    private static User operatorCustomerUser;
    private static User operatorDriverUser;
    private static User driverCustomerUser;

    private static User operatorDriverCustomerUser;

    private static void init() {
        operatorUser = new User(UUID.randomUUID().toString());
        operatorUser.addRole(new Operator(UUID.randomUUID().toString()));

        driverUser = new User(UUID.randomUUID().toString());
        driverUser.addRole(new Driver(UUID.randomUUID().toString()));

        customerUser = new User(UUID.randomUUID().toString());
        customerUser.addRole(new Customer(UUID.randomUUID().toString()));

        operatorCustomerUser = new User(UUID.randomUUID().toString());
        operatorCustomerUser.addRole(new Operator(UUID.randomUUID().toString()));
        operatorCustomerUser.addRole(new Customer(UUID.randomUUID().toString()));

        operatorDriverUser = new User(UUID.randomUUID().toString());
        operatorDriverUser.addRole(new Operator(UUID.randomUUID().toString()));
        operatorDriverUser.addRole(new Driver(UUID.randomUUID().toString()));

        driverCustomerUser = new User(UUID.randomUUID().toString());
        driverCustomerUser.addRole(new Driver(UUID.randomUUID().toString()));
        driverCustomerUser.addRole(new Customer(UUID.randomUUID().toString()));

        operatorDriverCustomerUser = new User(UUID.randomUUID().toString());
        operatorDriverCustomerUser.addRole(new Operator(UUID.randomUUID().toString()));
        operatorDriverCustomerUser.addRole(new Driver(UUID.randomUUID().toString()));
        operatorDriverCustomerUser.addRole(new Customer(UUID.randomUUID().toString()));

    }

    @Before
    public void setUp() {
        init();
    }

    public static class HasRoleTest {

        @Before
        public void setUp() {
            UserTest.init();
        }

        @Test
        public void testHasRoleOperator() {
            Role.Type roleToTest = Role.Type.OPERATOR;
            assertEquals(operatorUser.hasRole(roleToTest), true);
            assertEquals(operatorDriverUser.hasRole(roleToTest), true);
            assertEquals(operatorCustomerUser.hasRole(roleToTest), true);
            assertEquals(operatorDriverCustomerUser.hasRole(roleToTest), true);

            assertEquals(driverUser.hasRole(roleToTest), false);
            assertEquals(customerUser.hasRole(roleToTest), false);
            assertEquals(driverCustomerUser.hasRole(roleToTest), false);
        }

        @Test
        public void testHasRoleDriver() {
            Role.Type roleToTest = Role.Type.DRIVER;
            assertEquals(driverUser.hasRole(roleToTest), true);
            assertEquals(operatorDriverUser.hasRole(roleToTest), true);
            assertEquals(driverCustomerUser.hasRole(roleToTest), true);
            assertEquals(operatorDriverCustomerUser.hasRole(roleToTest), true);

            assertEquals(operatorUser.hasRole(roleToTest), false);
            assertEquals(customerUser.hasRole(roleToTest), false);
            assertEquals(operatorCustomerUser.hasRole(roleToTest), false);
        }

        @Test
        public void testHasRoleCustomer() {
            Role.Type roleToTest = Role.Type.CUSTOMER;
            assertEquals(customerUser.hasRole(roleToTest), true);
            assertEquals(operatorCustomerUser.hasRole(roleToTest), true);
            assertEquals(driverCustomerUser.hasRole(roleToTest), true);
            assertEquals(operatorDriverCustomerUser.hasRole(roleToTest), true);

            assertEquals(driverUser.hasRole(roleToTest), false);
            assertEquals(operatorUser.hasRole(roleToTest), false);
            assertEquals(operatorDriverUser.hasRole(roleToTest), false);
        }

    }

    public static class otherMethodsTest {

        @Before
        public void setUp() {
            UserTest.init();
        }

        @Test
        public void testAddRole() {
            driverUser.addRole(new Customer(UUID.randomUUID().toString()));
            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                driverUser.addRole(new Driver(UUID.randomUUID().toString()));
            });

        }

        @Test
        public void testRemoveRole() {
            // double remove call on Customer role
            operatorDriverCustomerUser.removeRole(new Customer(UUID.randomUUID().toString()));
            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                operatorDriverCustomerUser.removeRole(new Customer(UUID.randomUUID().toString()));
            }).withMessageContaining(Role.Type.CUSTOMER.toString());

            // double remove call on Driver role
            operatorDriverCustomerUser.removeRole(new Driver(UUID.randomUUID().toString()));
            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                operatorDriverCustomerUser.removeRole(new Driver(UUID.randomUUID().toString()));
            }).withMessageContaining(Role.Type.DRIVER.toString());

            // double remove call on Operator role
            operatorDriverCustomerUser.removeRole(new Operator(UUID.randomUUID().toString()));
            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                operatorDriverCustomerUser.removeRole(new Operator(UUID.randomUUID().toString()));
            }).withMessageContaining(Role.Type.OPERATOR.toString());

        }
    }

    public static class GetRolesMethodsTest {

        @Before
        public void setUp() {
            UserTest.init();
        }

        @Test
        public void testGetDriverRole() {
            assertEquals(driverUser.getDriverRole().getType(), Role.Type.DRIVER);
            assertEquals(operatorDriverUser.getDriverRole().getType(), Role.Type.DRIVER);
            assertEquals(driverCustomerUser.getDriverRole().getType(), Role.Type.DRIVER);
            assertEquals(operatorDriverCustomerUser.getDriverRole().getType(), Role.Type.DRIVER);

            assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
                operatorUser.getDriverRole();
            });
            assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
                customerUser.getDriverRole();
            });
            assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
                operatorCustomerUser.getDriverRole();
            });
        }

        @Test
        public void testGetCustomerRole() {
            assertEquals(customerUser.getCustomerRole().getType(), Role.Type.CUSTOMER);
            assertEquals(operatorCustomerUser.getCustomerRole().getType(), Role.Type.CUSTOMER);
            assertEquals(driverCustomerUser.getCustomerRole().getType(), Role.Type.CUSTOMER);
            assertEquals(operatorDriverCustomerUser.getCustomerRole().getType(), Role.Type.CUSTOMER);

            assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
                operatorUser.getCustomerRole();
            });
            assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
                driverUser.getCustomerRole();
            });
            assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
                operatorDriverUser.getCustomerRole();
            });
        }

        @Test
        public void testGetOperatorRole() {
            assertEquals(operatorUser.getOperatorRole().getType(), Role.Type.OPERATOR);
            assertEquals(operatorCustomerUser.getOperatorRole().getType(), Role.Type.OPERATOR);
            assertEquals(operatorDriverUser.getOperatorRole().getType(), Role.Type.OPERATOR);
            assertEquals(operatorDriverCustomerUser.getOperatorRole().getType(), Role.Type.OPERATOR);

            assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
                customerUser.getOperatorRole();
            });
            assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
                driverUser.getOperatorRole();
            });
            assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
                driverCustomerUser.getOperatorRole();
            });
        }
    }

    public static class IsRolesMethodsTest {

        @Before
        public void setUp() {
            UserTest.init();
        }

        @Test
        public void testIsOperator() {
            assertEquals(operatorUser.isOperator(), true);
            assertEquals(operatorDriverUser.isOperator(), true);
            assertEquals(operatorCustomerUser.isOperator(), true);
            assertEquals(operatorDriverCustomerUser.isOperator(), true);

            assertEquals(driverUser.isOperator(), false);
            assertEquals(customerUser.isOperator(), false);
            assertEquals(driverCustomerUser.isOperator(), false);
        }

        @Test
        public void testIsDriver() {
            assertEquals(driverUser.isDriver(), true);
            assertEquals(operatorDriverUser.isDriver(), true);
            assertEquals(driverCustomerUser.isDriver(), true);
            assertEquals(operatorDriverCustomerUser.isDriver(), true);

            assertEquals(operatorUser.isDriver(), false);
            assertEquals(customerUser.isDriver(), false);
            assertEquals(operatorCustomerUser.isDriver(), false);
        }

        @Test
        public void testIsCustomer() {
            assertEquals(customerUser.isCustomer(), true);
            assertEquals(operatorCustomerUser.isCustomer(), true);
            assertEquals(driverCustomerUser.isCustomer(), true);
            assertEquals(operatorDriverCustomerUser.isCustomer(), true);

            assertEquals(driverUser.isCustomer(), false);
            assertEquals(operatorUser.isCustomer(), false);
            assertEquals(operatorDriverUser.isCustomer(), false);
        }
    }
}
