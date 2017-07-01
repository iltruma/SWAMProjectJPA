package it.unifi.ing.swam.model;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class WaybillTest {

	private static Waybill w;
	
	private static void init() {
		String uuid = UUID.randomUUID().toString();
		w = new Waybill(uuid);
	}

	@Before
	public void setUp() { init(); }

	public static class SetSenderTest {
		
		@Before
		public void setUp() { init(); }

		@Test
		public void testSetSenderAsDriver() {
			User driver = new User(UUID.randomUUID().toString());
			driver.addRole(new Driver(UUID.randomUUID().toString()));

			assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
				w.setSender(driver);
			});
		}

		@Test
		public void testSetSenderAsOperator() {
			User operator = new User(UUID.randomUUID().toString());
			operator.addRole(new Operator(UUID.randomUUID().toString()));

			assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
				w.setSender(operator);
			});
		}
		
		@Test
		public void testSetSenderAsCustomer() {
			User customer = new User(UUID.randomUUID().toString());
			customer.addRole(new Customer(UUID.randomUUID().toString()));

			w.setSender(customer);
			assertEquals(w.getSender(), customer);
		}

	}
	
	public static class SetOperatorTest {
		
		@Before
		public void setUp() { init(); }

		@Test
		public void testSetOperatorAsDriver() {
			User driver = new User(UUID.randomUUID().toString());
			driver.addRole(new Driver(UUID.randomUUID().toString()));

			assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
				w.setOperator(driver);
			});
		}

		@Test
		public void testSetOperatorAsCustomer() {
			User customer = new User(UUID.randomUUID().toString());
			customer.addRole(new Customer(UUID.randomUUID().toString()));

			assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
				w.setOperator(customer);
			});
		}
		
		@Test
		public void testSetOperatorAsOperator() {
			User operator = new User(UUID.randomUUID().toString());
			operator.addRole(new Operator(UUID.randomUUID().toString()));

			w.setOperator(operator);
			assertEquals(w.getOperator(), operator);
		}


	}

}
