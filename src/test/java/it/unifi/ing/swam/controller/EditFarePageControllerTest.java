package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.bean.CustomerBean;
import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.dao.FareDao;
import it.unifi.ing.swam.model.Fare;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;

public class EditFarePageControllerTest extends BasicController {

    private EditFarePageController editFarePageController;
    private CustomerBean conversationBean;
    private UserSessionBean userSession;

    private FareDao fareDao;

    private Fare fare;
    private User user;
    private User wrongUser;
    private User customer;

    private Long fareId = 1L;
    private Long wrongFareId = 2L;

    @Before
    public void setUp() throws InitializationError {
        editFarePageController = new EditFarePageController();
        conversationBean = new CustomerBean();
        userSession = new UserSessionBean();

        fareDao = mock(FareDao.class);

        user = ModelFactory.generateUser();
        user.addRole(ModelFactory.generateOperator());

        wrongUser = ModelFactory.generateUser();
        wrongUser.addRole(ModelFactory.generateDriver());

        userSession.setUser(user);

        fare = ModelFactory.generateFare();

        when(fareDao.findById(fareId)).thenReturn(fare);
        when(fareDao.findById(wrongFareId)).thenReturn(null);

        customer = ModelFactory.generateUser();
        customer.addRole(ModelFactory.generateCustomer());
        customer.getCustomerRole().addFare(fare);

        conversationBean.setCustomer(customer);

        try {
            FieldUtils.writeField(editFarePageController, "fareId", fareId.toString(), true);
            FieldUtils.writeField(editFarePageController, "fareDao", fareDao, true);
            FieldUtils.writeField(editFarePageController, "userSession", userSession, true);
            FieldUtils.writeField(editFarePageController, "conversationBean", conversationBean, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
    }

    @Test
    public void testInitEditFarePage() {
        editFarePageController.initEditFarePage();

        assertEquals(fare, editFarePageController.getFare());
    }

    @Test
    public void testInitEditFarePageThrowsIllegalArgumentException() throws InitializationError {
        userSession.setUser(wrongUser);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            editFarePageController.initEditFarePage();
        }).withMessage("you cant view this page");

        userSession.setUser(user);
        try {
            FieldUtils.writeField(editFarePageController, "fareId", null, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            editFarePageController.initEditFarePage();
        }).withMessage("fare id is empty");

        try {
            FieldUtils.writeField(editFarePageController, "fareId", wrongFareId.toString(), true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            editFarePageController.initEditFarePage();
        }).withMessage("fare not found");

        customer.getCustomerRole().getFares().clear();
        try {
            FieldUtils.writeField(editFarePageController, "fareId", fareId.toString(), true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            editFarePageController.initEditFarePage();
        }).withMessage("fare not found for this customer");

        customer.getCustomerRole().addFare(fare);
        editFarePageController.initEditFarePage();
    }

    @Test
    public void testSave() throws InitializationError {

        try {
            FieldUtils.writeField(fare, "id", fareId, true);
            FieldUtils.writeField(editFarePageController, "fare", fare, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }

        assertEquals(editFarePageController.save(), "ViewFarePage" + fare.getId().toString());
    }

}
