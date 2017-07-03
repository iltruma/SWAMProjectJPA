package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;

public class LoginControllerTest {
	

	private LoginController loginController;
	private UserDao userDao;
	private UserSessionBean userSession;
	private User user;

	@Before
	public void init() throws InitializationError {
		loginController = new LoginController();
		userSession = new UserSessionBean();
		userDao = mock(UserDao.class);
		
		user = ModelFactory.generateUser();
		user.setPassword("password");
		user.setUsername("username");
		
		try {
			FieldUtils.writeField(user, "id", Long.valueOf(10), true);
			FieldUtils.writeField(loginController, "userDao", userDao, true);
			FieldUtils.writeField(loginController, "userSession", userSession, true);
		} catch (IllegalAccessException e) {
			throw new InitializationError(e);
		}
	}

	@Test
	public void testLoginSuccess() {
		when(userDao.findByLoginInfo(any(User.class))).thenReturn(user);
		
		String result = loginController.login(user);
		
		assertTrue(result.contains("Successfull"));
		assertEquals(user.getId(), userSession.getUserId());
		assertTrue(userSession.isLoggedIn());
	}
	
	@Test
	public void testLoginFail() {
		when(userDao.findByLoginInfo(any(User.class))).thenReturn(null);
		
		assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
			loginController.login(user);
		});
		
		assertNull(userSession.getUserId());
		assertFalse(userSession.isLoggedIn());
	}

}
