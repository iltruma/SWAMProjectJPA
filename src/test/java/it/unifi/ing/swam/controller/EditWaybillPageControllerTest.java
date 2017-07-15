package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.bean.UserSessionBean;
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
import it.unifi.ing.swam.model.State;
import it.unifi.ing.swam.model.Tracking;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

@RunWith(Enclosed.class)
public class EditWaybillPageControllerTest {

    private static EditWaybillPageController editWaybillPageController;

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

        agency = ModelFactory.generateAgency();

        user = ModelFactory.generateUser();
        user.addRole(ModelFactory.generateCustomer());
        user.addRole(ModelFactory.generateDriver());
        user.addRole(ModelFactory.generateOperator());
        user.setAgency(ModelFactory.generateAgency());
        userSession.setUser(user);

        wrongUser = ModelFactory.generateUser();
        wrongUser.addRole(ModelFactory.generateCustomer());
        wrongUser.addRole(ModelFactory.generateDriver());

        waybill = ModelFactory.generateWaybill();
        waybill.setSender(user);

        when(waybillDao.findById(waybillId)).thenReturn(waybill);

        try {
            FieldUtils.writeField(user, "id", Long.valueOf(10), true);
            FieldUtils.writeField(editWaybillPageController, "roleId", roleId.toString(), true);
            FieldUtils.writeField(editWaybillPageController, "waybillId", waybillId.toString(), true);
            FieldUtils.writeField(editWaybillPageController, "roleDao", roleDao, true);
            FieldUtils.writeField(editWaybillPageController, "userSession", userSession, true);
            FieldUtils.writeField(editWaybillPageController, "waybillDao", waybillDao, true);
            FieldUtils.writeField(editWaybillPageController, "agencyDao", agencyDao, true);
            FieldUtils.writeField(editWaybillPageController, "itemDao", itemDao, true);
            FieldUtils.writeField(editWaybillPageController, "userDao", userDao, true);
            FieldUtils.writeField(editWaybillPageController, "missionDao", missionDao, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
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
            // waybill has the Customer user as sender
            waybill.setSender(user);
            editWaybillPageController.initWaybill();
            assertEquals(editWaybillPageController.getWaybill(), waybill);
        }

        @Test
        public void testInitWaybillWrongSender() {
            when(roleDao.findById(roleId)).thenReturn(user.getCustomerRole());
            editWaybillPageController.initStrategy();
            // waybill has the wrong Customer user as sender
            waybill.setSender(wrongUser);
            assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
                editWaybillPageController.initWaybill();
            });
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
            editWaybillPageController.initStrategy();

            item = ModelFactory.generateItem();
            item.setVolume(Float.valueOf(0F));
            item.setWeigth(Float.valueOf(0F));
            waybill.setTracking(Tracking.IDLE);

            when(agencyDao.findById(agencyId)).thenReturn(agency);
            when(itemDao.findById(itemId)).thenReturn(item);

            when(agencyDao.findById(wrongAgencyId)).thenReturn(null);
            when(itemDao.findById(wrongItemId)).thenReturn(null);

            editWaybillPageController.initWaybill();
        }

        @Test
        public void testInitWaybillError() throws InitializationError {
            when(waybillDao.findById(waybillId)).thenReturn(null);
            assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
                editWaybillPageController.initWaybill();
            });

            try {
                FieldUtils.writeField(editWaybillPageController.getStrategy(), "waybillId", null, true);
            } catch (IllegalAccessException e) {
                throw new InitializationError(e);
            }
            editWaybillPageController.initWaybill();

            assertEquals(user, editWaybillPageController.getWaybill().getSender());
        }

        @Test
        public void testInitWaybillThrowsIllegalStateException() {
            user.getCustomerRole().setState(State.BLOCKED);

            assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
                editWaybillPageController.initWaybill();
            });
        }

        @Test
        public void testSetAgency() {
            editWaybillPageController.getStrategy().setAgency(null);
            assertNull(waybill.getReceiver().getDestinationAgency());

            editWaybillPageController.getStrategy().setAgency(agencyId);
            assertEquals(waybill.getReceiver().getDestinationAgency(), agency);

            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                editWaybillPageController.getStrategy().setAgency(wrongAgencyId);
            });
        }

        @Test
        public void testAddItem() {
            editWaybillPageController.getStrategy().setNewItem(null);
            assertEquals(0, waybill.getLoad().getItems().size());

            editWaybillPageController.getStrategy().setNewItem(itemId);
            assertEquals(1, waybill.getLoad().getItems().size());
            assertEquals(item, waybill.getLoad().getItems().iterator().next());

            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                editWaybillPageController.getStrategy().setNewItem(wrongItemId);
            });
        }

    }

    public static class DriverTest {

        @Before
        public void setUp() throws InitializationError {
            EditWaybillPageControllerTest.init();

            when(roleDao.findById(roleId)).thenReturn(user.getDriverRole());
            editWaybillPageController.initStrategy();

            mission = ModelFactory.generateMission();

            waybill.setTracking(Tracking.SHIPPING);
            mission.addWaybill(waybill);

            when(missionDao.findByDriverAndDate(eq(user), any(Calendar.class))).thenReturn(mission);

            editWaybillPageController.initWaybill();
        }

        @Test
        public void testInitWaybillError() throws InitializationError {
            when(waybillDao.findById(waybillId)).thenReturn(null);
            assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
                editWaybillPageController.initWaybill();
            });

            try {
                FieldUtils.writeField(editWaybillPageController.getStrategy(), "waybillId", null, true);
            } catch (IllegalAccessException e) {
                throw new InitializationError(e);
            }
            assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
                editWaybillPageController.initWaybill();
            });
        }

        @Test
        public void testInitWaybillThrowsIllegalStateException() {
            waybill.setTracking(Tracking.IDLE);

            assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
                editWaybillPageController.initWaybill();
            });
        }

        @Test
        public void testSignAndSave() {
            editWaybillPageController.signAndSave();

            assertEquals(waybill.isSigned(), true);
            assertEquals(waybill.getTracking(), Tracking.DELIVERED);
        }
    }

    public static class OperatorTest {

        @Before
        public void setUp() throws InitializationError {
            EditWaybillPageControllerTest.init();

            when(roleDao.findById(roleId)).thenReturn(user.getOperatorRole());
            editWaybillPageController.initStrategy();

            item = ModelFactory.generateItem();
            item.setVolume(Float.valueOf(0F));
            item.setWeigth(Float.valueOf(0F));
            waybill.setTracking(Tracking.IDLE);

            when(agencyDao.findById(agencyId)).thenReturn(agency);
            when(itemDao.findById(itemId)).thenReturn(item);
            when(userDao.findById(userId)).thenReturn(user);

            when(agencyDao.findById(wrongAgencyId)).thenReturn(null);
            when(itemDao.findById(wrongItemId)).thenReturn(null);
            when(userDao.findById(wrongUserId)).thenReturn(null);

            editWaybillPageController.initWaybill();
        }

        @Test
        public void testInitWaybillError() throws InitializationError {
            when(waybillDao.findById(waybillId)).thenReturn(null);
            assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
                editWaybillPageController.initWaybill();
            });

            try {
                FieldUtils.writeField(editWaybillPageController.getStrategy(), "waybillId", null, true);
            } catch (IllegalAccessException e) {
                throw new InitializationError(e);
            }
            editWaybillPageController.initWaybill();

            assertEquals(user, editWaybillPageController.getWaybill().getOperator());
        }

        @Test
        public void testInitWaybillThrowsIllegalStateException() {
            waybill.setTracking(Tracking.DELIVERED);

            assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
                editWaybillPageController.initWaybill();
            });
        }

        @Test
        public void testSetCustomer() {
            editWaybillPageController.getStrategy().setSender(userId);
            assertEquals(waybill.getSender(), user);

            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                editWaybillPageController.getStrategy().setSender(wrongUserId);
            });
        }

        @Test
        public void testSetAgency() {
            editWaybillPageController.getStrategy().setAgency(null);
            assertNull(waybill.getReceiver().getDestinationAgency());

            editWaybillPageController.getStrategy().setAgency(agencyId);
            assertEquals(waybill.getReceiver().getDestinationAgency(), agency);

            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                editWaybillPageController.getStrategy().setAgency(wrongAgencyId);
            });
        }

        @Test
        public void testAddItem() {
            editWaybillPageController.getStrategy().setNewItem(null);
            assertEquals(0, waybill.getLoad().getItems().size());

            editWaybillPageController.getStrategy().setNewItem(itemId);
            assertEquals(1, waybill.getLoad().getItems().size());
            assertEquals(item, waybill.getLoad().getItems().iterator().next());

            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                editWaybillPageController.getStrategy().setNewItem(wrongItemId);
            });
        }

        @Test
        public void testSave() {
            assertEquals(true, editWaybillPageController.save().contains("waybill-view"));
            assertEquals(waybill.getOperator(), user);
        }
    }
}
