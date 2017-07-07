package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.bean.CustomerBean;
import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.model.Agency;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;

public class EditCustomerPageControllerTest {

    private EditCustomerPageController editCustomerPageController;
    private CustomerBean conversationBean;
    protected UserSessionBean userSession;

    private User user;
    private User customer;
    private Agency agency;

    private User wrongUser;
    private Agency wrongAgency;

    @Before
    public void setUp() throws InitializationError {
        editCustomerPageController = new EditCustomerPageController();
        conversationBean = new CustomerBean();
        userSession = new UserSessionBean();

        agency = ModelFactory.generateAgency();

        user = ModelFactory.generateUser();
        user.addRole(ModelFactory.generateOperator());
        user.setAgency(agency);

        userSession.setUser(user);

        wrongAgency = ModelFactory.generateAgency();

        customer = ModelFactory.generateUser();
        customer.addRole(ModelFactory.generateCustomer());
        customer.setAgency(wrongAgency);

        wrongUser = ModelFactory.generateUser();
        wrongUser.addRole(ModelFactory.generateDriver());

        try {
            FieldUtils.writeField(editCustomerPageController, "userSession", userSession, true);
            FieldUtils.writeField(editCustomerPageController, "conversationBean", conversationBean, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
    }

    @Test
    public void testInitEditCustomerPage() {

        editCustomerPageController.initEditCustomerPage();

        assertTrue(editCustomerPageController.getCustomer().isCustomer());
        assertEquals(agency, editCustomerPageController.getCustomer().getAgency());
    }

    @Test
    public void testInitEditCustomerPageThrowsIllegalStateException() {
        conversationBean.setCustomer(customer);

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
            editCustomerPageController.initEditCustomerPage();
        });
    }

    @Test
    public void testInitEditCustomerPageThrowsIllegalArgumentException() {
        userSession.setUser(wrongUser);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            editCustomerPageController.initEditCustomerPage();
        });
    }

}
