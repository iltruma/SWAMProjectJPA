package it.unifi.ing.swam.model;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CustomerTest {

    private static Customer c;

    @Before
    public void setUp() {
        c = ModelFactory.generateCustomer();
    }

    @Test
    public void testSetOperatorAsDriver() {
        User driver = ModelFactory.generateUser();
        driver.addRole(ModelFactory.generateDriver());

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            c.setOperator(driver);
        });
    }

    @Test
    public void testSetOperatorAsCustomer() {
        User customer = ModelFactory.generateUser();
        customer.addRole(ModelFactory.generateCustomer());

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            c.setOperator(customer);
        });
    }

    @Test
    public void testSetOperatorAsOperator() {
        User operator = ModelFactory.generateUser();
        operator.addRole(ModelFactory.generateOperator());

        c.setOperator(operator);
        assertEquals(c.getOperator(), operator);
    }

    @Test
    public void testIsActive() {
        assertTrue(c.isActive());

        c.setState(State.BLOCKED);
        assertFalse(c.isActive());
    }

}
