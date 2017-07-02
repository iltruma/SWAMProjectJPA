package it.unifi.ing.swam.bean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.unifi.ing.swam.bean.UserSessionBean;

public class UserSessionBeanTest {

	private UserSessionBean userSession;
	
	@Before
	public void setUp() {
		userSession = new UserSessionBean();
	}
	
	@Test
	public void testIsLoggedIn() {
		assertFalse(userSession.isLoggedIn());
		
		userSession.setUserId(Long.valueOf(1));
		assertTrue(userSession.isLoggedIn());
	}
	
}
