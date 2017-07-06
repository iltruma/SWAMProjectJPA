package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
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

public class ViewFarePageControllerTest extends BasicController {

    private ViewFarePageController viewFarePageController;
    private UserSessionBean userSession;
    private CustomerBean conversationBean;
    private FareDao fareDao;
    private Long fareId;
    private Fare fare;

    private User user;
    private User wrongUser;
    private User customer;

    @Before
    public void setUp() throws InitializationError {
        viewFarePageController = new ViewFarePageController();
        userSession = new UserSessionBean();
        conversationBean = new CustomerBean();
        fareDao = mock(FareDao.class);

        user = ModelFactory.generateUser();
        user.addRole(ModelFactory.generateOperator());

        wrongUser = ModelFactory.generateUser();
        wrongUser.addRole(ModelFactory.generateCustomer());
        wrongUser.addRole(ModelFactory.generateDriver());

        customer = ModelFactory.generateUser();
        customer.addRole(ModelFactory.generateCustomer());

        conversationBean.setCustomer(customer);

        fare = ModelFactory.generateFare();

        when(fareDao.findById(1L)).thenReturn(fare);
        when(fareDao.findById(2L)).thenReturn(null);

        try {
            FieldUtils.writeField(user, "id", Long.valueOf(10), true);
            FieldUtils.writeField(viewFarePageController, "fareDao", fareDao, true);
            FieldUtils.writeField(viewFarePageController, "userSession", userSession, true);
            FieldUtils.writeField(viewFarePageController, "conversationBean", conversationBean, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
    }

    @Test
    public void testInitFaresPage() throws InitializationError {
        userSession.setUser(wrongUser);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            viewFarePageController.initFaresPage();
        }).withMessage("you cant view this page");

        userSession.setUser(user);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            viewFarePageController.initFaresPage();
        }).withMessage("fare id is empty");

        fareId = 2L;
        try {
            FieldUtils.writeField(viewFarePageController, "fareId", fareId.toString(), true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            viewFarePageController.initFaresPage();
        }).withMessage("fare not found");

        fareId = 1L;
        try {
            FieldUtils.writeField(viewFarePageController, "fareId", fareId.toString(), true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            viewFarePageController.initFaresPage();
        }).withMessage("fare not found for this customer");

        customer.getCustomerRole().addFare(fare);
        viewFarePageController.initFaresPage();
    }

}
