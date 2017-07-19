package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Model;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.controller.strategy.CustomerStrategy;
import it.unifi.ing.swam.controller.strategy.DriverStrategy;
import it.unifi.ing.swam.controller.strategy.OperatorStrategy;
import it.unifi.ing.swam.controller.strategy.RoleStrategy;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.RoleDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

@Model
@RunWith(Enclosed.class)
public class ViewWaybillPageControllerTest {

    private static ViewWaybillPageController viewWaybillPageController;
    private static WaybillDao waybillDao;
    private static MissionDao missionDao;
    private static RoleDao roleDao;
    private static UserSessionBean userSession;
    private static Waybill waybill;
    private static User user;
    private static User wrongUser;

    private static Long roleId = 11L;
    private static Long waybillId = 12L;
    
    private static Instance<RoleStrategy> roleStrategyInstance;

    @SuppressWarnings("unchecked")
	public static void init() throws InitializationError {
        viewWaybillPageController = new ViewWaybillPageController();
        userSession = new UserSessionBean();
        waybillDao = mock(WaybillDao.class);
        missionDao = mock(MissionDao.class);
        roleDao = mock(RoleDao.class);
        

        roleStrategyInstance = mock(Instance.class);

        user = ModelFactory.generateUser();
        user.setPassword("password");
        user.setUsername("username");
        user.addRole(ModelFactory.generateCustomer());
        user.addRole(ModelFactory.generateDriver());
        user.addRole(ModelFactory.generateOperator());
        userSession.setUser(user);

        wrongUser = ModelFactory.generateUser();
        wrongUser.addRole(ModelFactory.generateCustomer());
        wrongUser.addRole(ModelFactory.generateOperator());
        wrongUser.addRole(ModelFactory.generateDriver());

        waybill = ModelFactory.generateWaybill();

        when(waybillDao.findById(waybillId)).thenReturn(waybill);

        CustomerStrategy cs = new CustomerStrategy();
        OperatorStrategy os = new OperatorStrategy();
        DriverStrategy ds = new DriverStrategy();
        inject(cs);
        inject(ds);
        inject(os);
        
        Instance<CustomerStrategy> customerStrategyInstance = mock(Instance.class); 
        Instance<OperatorStrategy> operatorStrategyInstance = mock(Instance.class); 
        Instance<DriverStrategy> driverStrategyInstance = mock(Instance.class); 
        
        when(roleStrategyInstance.select(CustomerStrategy.class)).thenReturn(customerStrategyInstance);
        when(roleStrategyInstance.select(OperatorStrategy.class)).thenReturn(operatorStrategyInstance);
        when(roleStrategyInstance.select(DriverStrategy.class)).thenReturn(driverStrategyInstance);

        
        when(customerStrategyInstance.get()).thenReturn(cs);
        when(operatorStrategyInstance.get()).thenReturn(os);
        when(driverStrategyInstance.get()).thenReturn(ds);


        try {
            FieldUtils.writeField(user, "id", Long.valueOf(10), true);
            FieldUtils.writeField(viewWaybillPageController, "roleId", roleId.toString(), true);
            FieldUtils.writeField(viewWaybillPageController, "waybillId", waybillId.toString(), true);
            FieldUtils.writeField(viewWaybillPageController, "roleDao", roleDao, true);
            FieldUtils.writeField(viewWaybillPageController, "roleStrategyInstance", roleStrategyInstance, true);
            FieldUtils.writeField(viewWaybillPageController, "userSession", userSession, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
    }
    
    public static void inject(RoleStrategy rs) throws InitializationError {
        try {
            FieldUtils.writeField(rs, "waybillDao", waybillDao, true);
            FieldUtils.writeField(rs, "missionDao", missionDao, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
    }

    public static class CustomerTest {
        @Before
        public void setUp() throws InitializationError {
            ViewWaybillPageControllerTest.init();
            // set currentRole as Customer
            when(roleDao.findById(roleId)).thenReturn(user.getCustomerRole());
        }

        @Test
        public void testInitStrategy() throws InitializationError {
            // waybill has the Customer user as sender
            viewWaybillPageController.initStrategy();
            assertEquals(viewWaybillPageController.getStrategy().getClass(), CustomerStrategy.class);

            try {
                FieldUtils.writeField(viewWaybillPageController, "roleId", null, true);
            } catch (IllegalAccessException e) {
                throw new InitializationError(e);
            }

            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                viewWaybillPageController.initStrategy();
            }).withMessage("role id not found");

            try {
                FieldUtils.writeField(viewWaybillPageController, "roleId", roleId.toString(), true);
                FieldUtils.writeField(viewWaybillPageController, "waybillId", null, true);
            } catch (IllegalAccessException e) {
                throw new InitializationError(e);
            }

            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
                viewWaybillPageController.initStrategy();
            }).withMessage("waybill id not found");
        }

        @Test
        public void testInitWaybill() {
            // waybill has the Customer user as sender
            waybill.setSender(user);
            viewWaybillPageController.initWaybill();
            assertEquals(viewWaybillPageController.getWaybill(), waybill);
        }

        @Test
        public void testInitWaybillWrongSender() {
            viewWaybillPageController.initStrategy();
            // waybill has the wrong Customer user as sender
            waybill.setSender(wrongUser);
            assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
                viewWaybillPageController.initWaybill();
            });
        }
    }

    public static class DriverTest {
        private static Mission mission;

        @Before
        public void setUp() throws InitializationError {
            ViewWaybillPageControllerTest.init();

            // set currentRole as Driver
            when(roleDao.findById(roleId)).thenReturn(user.getDriverRole());

            mission = ModelFactory.generateMission();
            when(missionDao.findByDriverAndDate(eq(user), any(Calendar.class))).thenReturn(mission);
        }

        @Test
        public void testInitStrategy() {
            // waybill has the Customer user as sender
            viewWaybillPageController.initStrategy();
            assertEquals(viewWaybillPageController.getStrategy().getClass(), DriverStrategy.class);
        }

        @Test
        public void testInitWaybill() {
            viewWaybillPageController.initStrategy();
            // waybill is in the Driver's mission
            mission.addWaybill(waybill);
            viewWaybillPageController.initWaybill();
            assertEquals(viewWaybillPageController.getWaybill(), waybill);
        }

        @Test
        public void testInitWaybillWaybillNotInDriverMission() {
            viewWaybillPageController.initStrategy();
            // waybill is NOT in the Driver's mission
            assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
                viewWaybillPageController.initWaybill();
            });
        }
    }

    public static class OperatorTest {
        @Before
        public void setUp() throws InitializationError {
            ViewWaybillPageControllerTest.init();
            // set currentRole as Operator
            when(roleDao.findById(roleId)).thenReturn(user.getOperatorRole());
        }

        @Test
        public void testInitStrategy() {
            // waybill has the Customer user as sender
            viewWaybillPageController.initStrategy();
            assertEquals(viewWaybillPageController.getStrategy().getClass(), OperatorStrategy.class);
        }

        @Test
        public void testInitWaybill() {
            viewWaybillPageController.initStrategy();
            // waybill has the Operator user as operator
            waybill.setOperator(user);
            viewWaybillPageController.initWaybill();
            assertEquals(viewWaybillPageController.getWaybill(), waybill);
        }
    }
}
