package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Conversation;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.bean.CustomerBean;
import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.dao.CustomerDao;
import it.unifi.ing.swam.model.Customer;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;

public class CustomersPageControllerTest {

    private CustomersPageController customersPageController;
    private CustomerBean customerBean;
    private Conversation conversation;
    private UserSessionBean userSession;

    private CustomerDao customerDao;

    private User user;
    private User customer;

    @Before
    public void setUp() throws InitializationError {
        customersPageController = new CustomersPageController();
        customerBean = new CustomerBean();
        userSession = new UserSessionBean();

        conversation = mock(Conversation.class);

        try {
            FieldUtils.writeField(customerBean, "conversation", conversation, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }

        customerDao = mock(CustomerDao.class);

        user = ModelFactory.generateUser();
        user.addRole(ModelFactory.generateOperator());

        userSession.setUser(user);

        customer = ModelFactory.generateUser();
        Customer c = ModelFactory.generateCustomer();
        customer.addRole(c);

        List<Customer> customerList = new ArrayList<>();
        customerList.add(c);

        when(customerDao.findByOperator(user)).thenReturn(customerList);

        try {
            FieldUtils.writeField(customersPageController, "customerDao", customerDao, true);
            FieldUtils.writeField(customersPageController, "userSession", userSession, true);
            FieldUtils.writeField(customersPageController, "customerBean", customerBean, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
    }

    @Test
    public void testInitCustomersPage() {
        customersPageController.initCustomersPage();

        List<User> resultCustomers = customersPageController.getCustomers();

        assertEquals(1, resultCustomers.size());
        assertEquals(customer, resultCustomers.get(0));
    }

    @Test
    public void testInitCustomersPageThrowsIllegalArgumentException() throws InitializationError {

        User wrongUser = ModelFactory.generateUser();
        wrongUser.addRole(ModelFactory.generateDriver());

        userSession.setUser(wrongUser);

        try {
            FieldUtils.writeField(customersPageController, "userSession", userSession, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            customersPageController.initCustomersPage();
        });
    }

    @Test
    public void testSelectCustomer() {
        customersPageController.selectCustomer(customer);
        assertEquals(customer, customerBean.getCustomer());
    }

}
