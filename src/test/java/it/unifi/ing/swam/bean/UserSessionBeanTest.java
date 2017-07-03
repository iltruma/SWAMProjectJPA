package it.unifi.ing.swam.bean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.model.User;

public class UserSessionBeanTest {

	private UserSessionBean userSession;
	
	@Before
	public void setUp() {
		userSession = new UserSessionBean();
	}
	
	@Test
	public void testIsLoggedIn() {
		assertFalse(userSession.isLoggedIn());
		
		userSession.setUser(new User(UUID.randomUUID().toString()));
		assertTrue(userSession.isLoggedIn());
	}
	
}
