package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.bean.ConversationCustomerBean;
import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;

public class ViewCustomerPageControllerTest extends BasicController {

    private ViewCustomerPageController viewCustomerPageController;

    private ConversationCustomerBean conversationBean;
    private UserSessionBean userSession;

    private User user;
    private User customer;
    private User wrongUser;

    @Before
    public void setUp() throws InitializationError{
        viewCustomerPageController = new ViewCustomerPageController();
        conversationBean = new ConversationCustomerBean();
        userSession = new UserSessionBean();

        wrongUser = ModelFactory.generateUser();
        wrongUser.addRole(ModelFactory.generateDriver());

        user = ModelFactory.generateUser();
        user.addRole(ModelFactory.generateOperator());

        customer = ModelFactory.generateUser();
        customer.addRole(ModelFactory.generateCustomer());

        try {
            FieldUtils.writeField(viewCustomerPageController, "userSession", userSession, true);
            FieldUtils.writeField(viewCustomerPageController, "conversationBean", conversationBean, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
    }

    @Test
    public void testInitCustomersPage() {
        userSession.setUser(wrongUser);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            viewCustomerPageController.initCustomersPage();
        }).withMessage("you cant view this page");

        userSession.setUser(user);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            viewCustomerPageController.initCustomersPage();
        }).withMessage("customer not found");

        conversationBean.setCustomer(customer);
        viewCustomerPageController.initCustomersPage();
    }

}
