package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.dao.RoleDao;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;


public class SearchPageControllerTest {

    private SearchPageController searchPageController;
    private UserSessionBean userSession;

    private UserDao userDao;
    
    private RoleDao roleDao;

    private Long userId = 1L;
    private Long roleId = 4L;

    private Long operatorId = 2L;
    private Long customerId = 3L;

    private User operator;
    private User customer;
    private User user;
    private User wrongUser;

    @Before
    public void setUp() throws InitializationError {
        searchPageController = new SearchPageController();
        userSession = new UserSessionBean();

        userDao = mock(UserDao.class);
        roleDao = mock(RoleDao.class);

        user = ModelFactory.generateUser();
        user.addRole(ModelFactory.generateOperator());
        userSession.setUser(user);

        wrongUser = ModelFactory.generateUser();
        wrongUser.addRole(ModelFactory.generateDriver());

        operator = ModelFactory.generateUser();
        operator.addRole(ModelFactory.generateOperator());

        customer = ModelFactory.generateUser();
        customer.addRole(ModelFactory.generateCustomer());

        try {
            FieldUtils.writeField(user, "id", userId, true);
            FieldUtils.writeField(searchPageController, "userDao", userDao, true);
            FieldUtils.writeField(searchPageController, "roleId", roleId.toString(), true);
            FieldUtils.writeField(searchPageController, "roleDao", roleDao, true);
            FieldUtils.writeField(searchPageController, "userSession", userSession, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }

    }

    @Test
    public void testInitSearchPage() {
        searchPageController.initSearchPage();
        assertNotNull(searchPageController.getWaybillQuery());
    }

    @Test
    public void testInitSearchPageThrowsIllegalArgumentException() {
        userSession.setUser(wrongUser);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            searchPageController.initSearchPage();
        });

    }

    @Test
    public void testSetOperator() {
        when(userDao.findById(operatorId)).thenReturn(operator);
        searchPageController.initSearchPage();
        searchPageController.setOperator(operatorId);
        assertEquals(operator, searchPageController.getWaybillQuery().getOperator());
    }

    @Test
    public void testSetOperatorThrowsIllegalArgumentException() {
        when(userDao.findById(operatorId)).thenReturn(customer);
        searchPageController.initSearchPage();

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            searchPageController.setOperator(operatorId);
        });

    }

    @Test
    public void testSetSender() {
        when(userDao.findById(customerId)).thenReturn(customer);
        searchPageController.initSearchPage();
        searchPageController.setSender(customerId);
        assertEquals(customer, searchPageController.getWaybillQuery().getSender());
    }

    @Test
    public void testSetSenderThrowsIllegalArgumentException() {
        when(userDao.findById(customerId)).thenReturn(operator);
        searchPageController.initSearchPage();

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            searchPageController.setSender(customerId);
        });

    }

}
