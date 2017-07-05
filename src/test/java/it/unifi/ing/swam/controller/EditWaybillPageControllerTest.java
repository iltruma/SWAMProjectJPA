package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import javax.enterprise.inject.Model;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.controller.strategy.RoleStrategy;
import it.unifi.ing.swam.dao.AgencyDao;
import it.unifi.ing.swam.dao.ItemDao;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.RoleDao;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Agency;
import it.unifi.ing.swam.model.Item;
import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.Tracking;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

@Model
@RunWith(Enclosed.class)
public class EditWaybillPageControllerTest extends BasicController {

    private static EditWaybillPageController editWaybillPageController;

    private static RoleStrategy roleStrategy;

    private static RoleDao roleDao;
    private static WaybillDao waybillDao;
    private static AgencyDao agencyDao;
    private static ItemDao itemDao;
    private static UserDao userDao;
    private static MissionDao missionDao;
    private static UserSessionBean userSession;

    private static Agency agency;
    private static Item item;
    private static Waybill waybill;
    private static Mission mission;
    private static User user;
    private static User wrongUser;

    private static Long roleId = 11L;
    private static Long waybillId = 12L;
    private static Long agencyId = 13L;
    private static Long itemId = 14L;
    private static Long userId = 15L;
    private static Long wrongAgencyId = 16L;
    private static Long wrongItemId = 17L;
    private static Long wrongUserId = 18L;

    public static void init() throws InitializationError {
        editWaybillPageController = new EditWaybillPageController();

        userSession = new UserSessionBean();
        roleDao = mock(RoleDao.class);
        waybillDao = mock(WaybillDao.class);
        agencyDao = mock(AgencyDao.class);
        itemDao = mock(ItemDao.class);
        userDao = mock(UserDao.class);
        missionDao = mock(MissionDao.class);

        user = ModelFactory.generateUser();
        user.addRole(ModelFactory.generateCustomer());
        user.addRole(ModelFactory.generateDriver());
        user.addRole(ModelFactory.generateOperator());
        userSession.setUser(user);

        wrongUser = ModelFactory.generateUser();
        wrongUser.addRole(ModelFactory.generateCustomer());
        wrongUser.addRole(ModelFactory.generateDriver());

        waybill = ModelFactory.generateWaybill();

        when(waybillDao.findById(waybillId)).thenReturn(waybill);

        try {
            FieldUtils.writeField(user, "id", Long.valueOf(10), true);
            FieldUtils.writeField(editWaybillPageController, "roleId", roleId.toString(), true);
            FieldUtils.writeField(editWaybillPageController, "waybillId", waybillId.toString(), true);
            FieldUtils.writeField(editWaybillPageController, "roleDao", roleDao, true);
            FieldUtils.writeField(editWaybillPageController, "userSession", userSession, true);

        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
    }

    protected static void initStrategy() {
        editWaybillPageController.initStrategy();
        RoleStrategy strategy = editWaybillPageController.getStrategy();

        try {
            FieldUtils.writeField(strategy, "waybillDao", waybillDao, true);
        } catch (IllegalAccessException e) {
        }
    }

    public static class MainTest {
        @Before
        public void setUp() throws InitializationError {
            EditWaybillPageControllerTest.init();
        }

        @Test
        public void testInitWaybill() {
            when(roleDao.findById(roleId)).thenReturn(user.getCustomerRole());
            EditWaybillPageControllerTest.initStrategy();
            // waybill has the Customer user as sender
            waybill.setSender(user);
            editWaybillPageController.initWaybill();
            assertEquals(editWaybillPageController.getWaybill(), waybill);
        }

        @Test
        public void testInitWaybillWrongSender() {
            when(roleDao.findById(roleId)).thenReturn(user.getCustomerRole());
            EditWaybillPageControllerTest.initStrategy();
            // waybill has the wrong Customer user as sender
            waybill.setSender(wrongUser);
            assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
                editWaybillPageController.initWaybill();
            });
            assertNull(editWaybillPageController.getWaybill());
        }

        @Test
        public void testInitStrategy() throws InitializationError {
            try {
                FieldUtils.writeField(editWaybillPageController, "roleId", "", true);
                FieldUtils.writeField(editWaybillPageController, "waybillId", waybillId.toString(), true);
            } catch (IllegalAccessException e) {
                throw new InitializationError(e);
            }
            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                editWaybillPageController.initStrategy();
            });

            try {
                FieldUtils.writeField(editWaybillPageController, "roleId", roleId.toString(), true);
                FieldUtils.writeField(editWaybillPageController, "waybillId", "", true);
            } catch (IllegalAccessException e) {
                throw new InitializationError(e);
            }
            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                editWaybillPageController.initStrategy();
            });
        }
    }

    public static class CustomerTest {

        @Before
        public void setUp() throws InitializationError {
            EditWaybillPageControllerTest.init();

            when(roleDao.findById(roleId)).thenReturn(user.getCustomerRole());
            EditWaybillPageControllerTest.initStrategy();

            agency = ModelFactory.generateAgency();
            item = ModelFactory.generateItem();
            item.setVolume(Float.valueOf(0F));
            item.setWeigth(Float.valueOf(0F));
            waybill.setSender(user);

            when(agencyDao.findById(agencyId)).thenReturn(agency);
            when(itemDao.findById(itemId)).thenReturn(item);

            when(agencyDao.findById(wrongAgencyId)).thenReturn(null);
            when(itemDao.findById(wrongItemId)).thenReturn(null);

            roleStrategy = editWaybillPageController.getStrategy();

            try {
                FieldUtils.writeField(roleStrategy, "waybillDao", waybillDao, true);
                FieldUtils.writeField(roleStrategy, "agencyDao", agencyDao, true);
                FieldUtils.writeField(roleStrategy, "itemDao", itemDao, true);
                FieldUtils.writeField(roleStrategy, "userDao", userDao, true);
            } catch (IllegalAccessException e) {
                throw new InitializationError(e);
            }

            roleStrategy.initWaybill();
        }

        @Test
        public void testSetAgency() {
            roleStrategy.setAgency(agencyId);
            assertEquals(waybill.getReceiver().getDestinationAgency(), agency);

            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                roleStrategy.setAgency(wrongAgencyId);
            });
        }

        @Test
        public void testAddItem() {
            roleStrategy.addItem(itemId);
            assertEquals(1, waybill.getLoad().getItems().size());
            assertEquals(item, waybill.getLoad().getItems().get(0));

            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                roleStrategy.addItem(wrongItemId);
            });
        }

    }

    public static class DriverTest {

        @Before
        public void setUp() throws InitializationError {
            EditWaybillPageControllerTest.init();

            when(roleDao.findById(roleId)).thenReturn(user.getDriverRole());
            EditWaybillPageControllerTest.initStrategy();

            mission = ModelFactory.generateMission();

            waybill.setSender(user);
            mission.addWaybill(waybill);

            when(missionDao.findByDriverAndDate(eq(user), any(Calendar.class))).thenReturn(mission);
            roleStrategy = editWaybillPageController.getStrategy();

            try {
                FieldUtils.writeField(roleStrategy, "waybillDao", waybillDao, true);
                FieldUtils.writeField(roleStrategy, "agencyDao", agencyDao, true);
                FieldUtils.writeField(roleStrategy, "itemDao", itemDao, true);
                FieldUtils.writeField(roleStrategy, "userDao", userDao, true);
                FieldUtils.writeField(roleStrategy, "missionDao", missionDao, true);
            } catch (IllegalAccessException e) {
                throw new InitializationError(e);
            }

            roleStrategy.initWaybill();
        }

        @Test
        public void testSetSignAndTracking() {
            roleStrategy.setSignAndTracking();

            assertEquals(waybill.isSigned(), true);
            assertEquals(waybill.getTracking(), Tracking.DELIVERED);
        }
    }

    public static class OperatorTest {

        @Before
        public void setUp() throws InitializationError {
            EditWaybillPageControllerTest.init();

            when(roleDao.findById(roleId)).thenReturn(user.getOperatorRole());
            EditWaybillPageControllerTest.initStrategy();

            agency = ModelFactory.generateAgency();
            item = ModelFactory.generateItem();
            item.setVolume(Float.valueOf(0F));
            item.setWeigth(Float.valueOf(0F));
            waybill.setSender(user);

            when(agencyDao.findById(agencyId)).thenReturn(agency);
            when(itemDao.findById(itemId)).thenReturn(item);
            when(userDao.findById(userId)).thenReturn(user);

            when(agencyDao.findById(wrongAgencyId)).thenReturn(null);
            when(itemDao.findById(wrongItemId)).thenReturn(null);
            when(userDao.findById(wrongUserId)).thenReturn(null);

            roleStrategy = editWaybillPageController.getStrategy();

            try {
                FieldUtils.writeField(roleStrategy, "waybillDao", waybillDao, true);
                FieldUtils.writeField(roleStrategy, "agencyDao", agencyDao, true);
                FieldUtils.writeField(roleStrategy, "itemDao", itemDao, true);
                FieldUtils.writeField(roleStrategy, "userDao", userDao, true);
            } catch (IllegalAccessException e) {
                throw new InitializationError(e);
            }

            roleStrategy.initWaybill();
        }

        @Test
        public void testSetCustomer() {
            roleStrategy.setCustomer(userId);
            assertEquals(waybill.getSender(), user);

            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                roleStrategy.setCustomer(wrongUserId);
            });
        }

        @Test
        public void testSetAgency() {
            roleStrategy.setAgency(agencyId);
            assertEquals(waybill.getReceiver().getDestinationAgency(), agency);

            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                roleStrategy.setAgency(wrongAgencyId);
            });
        }

        @Test
        public void testAddItem() {
            roleStrategy.addItem(itemId);
            assertEquals(1, waybill.getLoad().getItems().size());
            assertEquals(item, waybill.getLoad().getItems().get(0));

            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                roleStrategy.addItem(wrongItemId);
            });
        }

        @Test
        public void testSave() {
            assertEquals(roleStrategy.save(), "ViewPage" + waybill.getId() + user.getCustomerRole().getId());
            assertEquals(waybill.getOperator(), user);
        }
    }
}
