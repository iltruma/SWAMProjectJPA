package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.enterprise.context.Conversation;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.bean.MissionBean;
import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.dao.DriverDao;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Driver;
import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

public class AssignDriverPageControllerTest extends BasicController {

    private AssignDriverPageController assignDriverPageController;
    private MissionBean conversationBean;
    private Conversation conversation;
    private UserSessionBean userSession;

    private WaybillDao waybillDao;
    private DriverDao driverDao;
    private MissionDao missionDao;

    private Waybill waybill;
    private Mission mission;
    private User user;
    private User driver;

    @Before
    public void setUp() throws InitializationError {
        assignDriverPageController = new AssignDriverPageController();
        conversationBean = new MissionBean();
        userSession = new UserSessionBean();
        conversation = mock(Conversation.class);

        try {
            FieldUtils.writeField(conversationBean, "conversation", conversation, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }

        waybillDao = mock(WaybillDao.class);
        driverDao = mock(DriverDao.class);
        missionDao = mock(MissionDao.class);

        user = ModelFactory.generateUser();
        user.addRole(ModelFactory.generateOperator());
        user.setAgency(ModelFactory.generateAgency());

        userSession.setUser(user);

        driver = ModelFactory.generateUser();
        Driver d = ModelFactory.generateDriver();
        driver.addRole(d);

        List<Waybill> waybillList = new ArrayList<>();
        waybillList.add(waybill);
        List<Driver> driverList = new ArrayList<>();
        driverList.add(d);

        mission = ModelFactory.generateMission();

        when(waybillDao.findUnassignedToDriver(user.getAgency())).thenReturn(waybillList);
        when(driverDao.findAvailable(user.getAgency())).thenReturn(driverList);
        when(missionDao.findByDriverAndDate(eq(driver), any(Calendar.class))).thenReturn(mission);

        try {
            FieldUtils.writeField(assignDriverPageController, "waybillDao", waybillDao, true);
            FieldUtils.writeField(assignDriverPageController, "driverDao", driverDao, true);
            FieldUtils.writeField(assignDriverPageController, "missionDao", missionDao, true);
            FieldUtils.writeField(assignDriverPageController, "userSession", userSession, true);
            FieldUtils.writeField(assignDriverPageController, "conversationBean", conversationBean, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
    }

    @Test
    public void testInitAssignDriverPage() {

        assignDriverPageController.initAssignDriverPage();

        List<Waybill> resultWaybills = assignDriverPageController.getWaybills();
        List<Driver> resultDrivers = assignDriverPageController.getDriversAvailable();

        assertEquals(1, resultWaybills.size());
        assertEquals(waybill, resultWaybills.get(0));

        assertEquals(1, resultDrivers.size());
        assertEquals(driver.getDriverRole(), resultDrivers.get(0));
    }

    @Test
    public void testInitAssignDriverPageThrowsIllegalArgumentException() {
        userSession.setUser(driver);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            assignDriverPageController.initAssignDriverPage();
        });
    }

    @Test
    public void testSelectDriver() throws InitializationError {

        assignDriverPageController.selectDriver(driver.getDriverRole());
        assertEquals(mission, conversationBean.getMission());

        when(missionDao.findByDriverAndDate(eq(driver), any(Calendar.class))).thenReturn(null);
        try {
            FieldUtils.writeField(assignDriverPageController, "missionDao", missionDao, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }

        assignDriverPageController.selectDriver(driver.getDriverRole());

    }

}
