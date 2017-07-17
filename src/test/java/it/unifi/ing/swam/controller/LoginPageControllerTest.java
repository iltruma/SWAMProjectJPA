package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.enterprise.context.Conversation;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.bean.CustomerBean;
import it.unifi.ing.swam.bean.MissionBean;
import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;

public class LoginPageControllerTest {

    private LoginPageController loginController;

    private Conversation conversation;
    private CustomerBean customerBean;
    private MissionBean missionBean;

    private UserDao userDao;
    private UserSessionBean userSession;

    private User user;

    @Before
    public void init() throws InitializationError {
        loginController = new LoginPageController();

        userSession = new UserSessionBean();
        userDao = mock(UserDao.class);

        customerBean = new CustomerBean();
        missionBean = new MissionBean();
        conversation = mock(Conversation.class);

        user = ModelFactory.generateUser("username", "password");

        try {
            FieldUtils.writeField(user, "id", Long.valueOf(10), true);
            FieldUtils.writeField(customerBean, "conversation", conversation, true);
            FieldUtils.writeField(missionBean, "conversation", conversation, true);
            FieldUtils.writeField(loginController, "userDao", userDao, true);
            FieldUtils.writeField(loginController, "userSession", userSession, true);
            FieldUtils.writeField(loginController, "customerBean", customerBean, true);
            FieldUtils.writeField(loginController, "missionBean", missionBean, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
    }

    @Test
    public void testLoginSuccess() {
        when(userDao.findByLoginInfo(any(User.class))).thenReturn(user);

        loginController.login();

        assertEquals(user, userSession.getUser());
        assertTrue(userSession.isLoggedIn());
    }

    @Test
    public void testLoginFail() {
        when(userDao.findByLoginInfo(any(User.class))).thenReturn(null);

        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
            loginController.login();
        });

        assertNull(userSession.getUser());
        assertFalse(userSession.isLoggedIn());
    }

    @Test
    public void testLogout() {
        loginController.logout();

        assertNull(userSession.getUser());
    }

}
