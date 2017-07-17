package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.bean.CustomerBean;
import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.model.Fare;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;

public class ViewFarePageControllerTest {

    private ViewFarePageController viewFarePageController;
    private UserSessionBean userSession;
    private CustomerBean conversationBean;

    private Long userId = 1L;
    private Long fareId = 2L;

    private Fare fare;

    private User user;
    private User wrongUser;
    private User customer;

    @Before
    public void setUp() throws InitializationError {
        viewFarePageController = new ViewFarePageController();
        userSession = new UserSessionBean();
        conversationBean = new CustomerBean();

        user = ModelFactory.generateUser();
        user.addRole(ModelFactory.generateOperator());

        wrongUser = ModelFactory.generateUser();
        wrongUser.addRole(ModelFactory.generateCustomer());
        wrongUser.addRole(ModelFactory.generateDriver());

        customer = ModelFactory.generateUser();
        customer.addRole(ModelFactory.generateCustomer());

        conversationBean.setCustomer(customer);

        fare = ModelFactory.generateFare();
        customer.getCustomerRole().addFare(fare);

        try {
            FieldUtils.writeField(user, "id", userId, true);
            FieldUtils.writeField(fare, "id", fareId, true);
            FieldUtils.writeField(viewFarePageController, "userSession", userSession, true);
            FieldUtils.writeField(viewFarePageController, "customerBean", conversationBean, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
    }

    @Test
    public void testInitFaresPage() throws InitializationError {
        userSession.setUser(wrongUser);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            viewFarePageController.initFarePage();
        }).withMessage("you cant view this page");

        userSession.setUser(user);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            viewFarePageController.initFarePage();
        }).withMessage("fare id is empty");

        Long wrongFareId = 3L;
        try {
            FieldUtils.writeField(viewFarePageController, "fareId", wrongFareId.toString(), true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            viewFarePageController.initFarePage();
        }).withMessage("fare not found for this customer");

        try {
            FieldUtils.writeField(viewFarePageController, "fareId", fareId.toString(), true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
        viewFarePageController.initFarePage();
    }

}
