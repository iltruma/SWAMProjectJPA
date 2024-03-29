package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Collections;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Agency;
import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

public class HomePageControllerTest {

    private HomePageController homePageController;
    private WaybillDao waybillDao;
    private MissionDao missionDao;
    private UserSessionBean userSession;
    private User user;
    private Waybill waybill;
    private Agency agency;

    @Before
    public void init() throws InitializationError {
        homePageController = new HomePageController();
        userSession = new UserSessionBean();
        missionDao = mock(MissionDao.class);
        waybillDao = mock(WaybillDao.class);

        user = ModelFactory.generateUser();
        user.setPassword("password");
        user.setUsername("username");
        userSession.setUser(user);

        waybill = ModelFactory.generateWaybill();
        agency = ModelFactory.generateAgency();

        try {
            FieldUtils.writeField(user, "id", Long.valueOf(10), true);
            FieldUtils.writeField(homePageController, "missionDao", missionDao, true);
            FieldUtils.writeField(homePageController, "waybillDao", waybillDao, true);
            FieldUtils.writeField(homePageController, "userSession", userSession, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
    }

    @Test
    public void testCheckIsLogged() {
        homePageController.checkIsLogged();
        userSession.setUser(null); // logout
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
            homePageController.checkIsLogged();
            ;
        });
    }

    @Test
    public void testGetProposedWaybillCustomer() {
        when(waybillDao.findProposedBySender(user)).thenReturn(Collections.singletonList(waybill));
        user.addRole(ModelFactory.generateCustomer());

        assertEquals(1, homePageController.getProposedWaybillCustomer().size());
        assertEquals(waybill, homePageController.getProposedWaybillCustomer().get(0));

        User driver = ModelFactory.generateUser();
        driver.addRole(ModelFactory.generateDriver());
        userSession.setUser(driver);
        assertNull(homePageController.getProposedWaybillCustomer());

        User operator = ModelFactory.generateUser();
        operator.addRole(ModelFactory.generateDriver());
        userSession.setUser(operator);
        assertNull(homePageController.getProposedWaybillCustomer());
    }

    @Test
    public void testGetValidatedWaybillCustomer() {
        when(waybillDao.findValidatedBySender(user)).thenReturn(Collections.singletonList(waybill));
        user.addRole(ModelFactory.generateCustomer());

        assertEquals(1, homePageController.getValidatedWaybillCustomer().size());
        assertEquals(waybill, homePageController.getValidatedWaybillCustomer().get(0));

        User driver = ModelFactory.generateUser();
        driver.addRole(ModelFactory.generateDriver());
        userSession.setUser(driver);
        assertNull(homePageController.getValidatedWaybillCustomer());

        User operator = ModelFactory.generateUser();
        operator.addRole(ModelFactory.generateDriver());
        userSession.setUser(operator);
        assertNull(homePageController.getValidatedWaybillCustomer());
    }

    @Test
    public void testGetProposedWaybillOperator() {
        when(waybillDao.findProposedByAgency(agency)).thenReturn(Collections.singletonList(waybill));
        user.addRole(ModelFactory.generateOperator());

        assertEquals(0, homePageController.getProposedWaybillOperator().size());
        user.setAgency(agency);
        assertEquals(1, homePageController.getProposedWaybillOperator().size());
        assertEquals(waybill, homePageController.getProposedWaybillOperator().get(0));

        User driver = ModelFactory.generateUser();
        driver.addRole(ModelFactory.generateDriver());
        userSession.setUser(driver);
        assertNull(homePageController.getProposedWaybillOperator());

        User customer = ModelFactory.generateUser();
        customer.addRole(ModelFactory.generateCustomer());
        userSession.setUser(customer);
        assertNull(homePageController.getProposedWaybillOperator());
    }

    @Test
    public void testGetUnassignedToDriver() {
        when(waybillDao.findUnassignedToDriver(agency)).thenReturn(Collections.singletonList(waybill));
        user.addRole(ModelFactory.generateOperator());

        assertEquals(0, homePageController.getUnassignedToDriver().size());
        user.setAgency(agency);
        assertEquals(1, homePageController.getUnassignedToDriver().size());
        assertEquals(waybill, homePageController.getUnassignedToDriver().get(0));

        User driver = ModelFactory.generateUser();
        driver.addRole(ModelFactory.generateDriver());
        userSession.setUser(driver);
        assertNull(homePageController.getUnassignedToDriver());

        User customer = ModelFactory.generateUser();
        customer.addRole(ModelFactory.generateCustomer());
        userSession.setUser(customer);
        assertNull(homePageController.getUnassignedToDriver());
    }

    @Test
    public void testGetTodayMission() {
        Mission mission = ModelFactory.generateMission();
        mission.addWaybill(waybill);

        user.addRole(ModelFactory.generateDriver());

        when(missionDao.findByDriverAndDate(eq(user), any(Calendar.class))).thenReturn(null);
        assertEquals(0, homePageController.getTodayMission().size());

        when(missionDao.findByDriverAndDate(eq(user), any(Calendar.class))).thenReturn(mission);
        assertEquals(1, homePageController.getTodayMission().size());
        assertEquals(waybill, homePageController.getTodayMission().get(0));

        User operator = ModelFactory.generateUser();
        operator.addRole(ModelFactory.generateOperator());
        userSession.setUser(operator);
        assertNull(homePageController.getTodayMission());

        User customer = ModelFactory.generateUser();
        customer.addRole(ModelFactory.generateCustomer());
        userSession.setUser(customer);
        assertNull(homePageController.getTodayMission());
    }
}
