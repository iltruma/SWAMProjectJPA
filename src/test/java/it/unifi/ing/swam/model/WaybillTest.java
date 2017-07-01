package it.unifi.ing.swam.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import it.unifi.ing.swam.model.BaseEntity;

@RunWith(Enclosed.class)
public class WaybillTest {

	private static Waybill w;
	
	@Before
	public void setUp() {
		String uuid = UUID.randomUUID().toString();
		w = new Waybill(uuid);
	}
	
	public static class SetSenderTest {
		
		@Test(expected=IllegalArgumentException.class)
		public void testSetSenderAsDriver() {
			User driver = new User(UUID.randomUUID().toString());
			driver.addRole(new Customer(UUID.randomUUID().toString()));
			
			w.setSender(driver);
		}
		
		@Test(expected=IllegalArgumentException.class)
		public void testSetSenderAsOperator() {
			User operator = new User(UUID.randomUUID().toString());
			operator.addRole(new Operator(UUID.randomUUID().toString()));
			
			w.setSender(operator);
		}
		
	}

	
	
}
