package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
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
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.RoleDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

@RunWith(Enclosed.class)
@Model
public class ViewWaybillPageControllerTest extends BasicController {
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


	public static void init() throws InitializationError {
		viewWaybillPageController = new ViewWaybillPageController();
		userSession = new UserSessionBean();
		waybillDao = mock(WaybillDao.class);
		missionDao = mock(MissionDao.class);
		roleDao = mock(RoleDao.class);

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
		
		when(waybillDao.findById(12L)).thenReturn(waybill);
		try {
			FieldUtils.writeField(user, "id", Long.valueOf(10), true);
			FieldUtils.writeField(viewWaybillPageController, "roleId", roleId.toString(), true);
			FieldUtils.writeField(viewWaybillPageController, "waybillId", waybillId.toString(), true);
			FieldUtils.writeField(viewWaybillPageController, "roleDao", roleDao, true);
			FieldUtils.writeField(viewWaybillPageController, "userSession", userSession, true);

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

			viewWaybillPageController.initStrategy();
			RoleStrategy strategy = viewWaybillPageController.getStrategy();

			try {
				FieldUtils.writeField(strategy, "waybillDao", waybillDao, true);
			} catch (IllegalAccessException e) {
				throw new InitializationError(e);
			}
		}
		@Test
		public void testInitWaybill() {
			// waybill has the Customer user as sender
			waybill.setSender(user);
			viewWaybillPageController.initWaybill();
		}

		@Test
		public void testInitWaybillWrongSender() {
			// waybill has the wrong Customer user as sender
			waybill.setSender(wrongUser);
			assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
				viewWaybillPageController.initWaybill();
			});
		}
		
		@Test
		public void testGetWaybill() {
			this.testInitWaybill();
			assertEquals(viewWaybillPageController.getWaybill(), waybill);
		}
		
		@Test
		public void testGetWaybillWrongSender() {
			this.testInitWaybillWrongSender();
			assertNull(viewWaybillPageController.getWaybill());
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

			viewWaybillPageController.initStrategy();
			RoleStrategy strategy = viewWaybillPageController.getStrategy();

			try {
				FieldUtils.writeField(strategy, "waybillDao", waybillDao, true);
				FieldUtils.writeField(strategy, "missionDao", missionDao, true);
			} catch (IllegalAccessException e) {
				throw new InitializationError(e);
			}
		}
		@Test
		public void testInitWaybill() {
			// waybill is in the Driver's mission
			mission.addWaybill(waybill);
			viewWaybillPageController.initWaybill();
		}

		@Test
		public void testInitWaybillWaybillNotInDriverMission() {
			// waybill is NOT in the Driver's mission
			assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
				viewWaybillPageController.initWaybill();
			});
		}
		
		@Test
		public void testGetWaybill() {
			this.testInitWaybill();
			assertEquals(viewWaybillPageController.getWaybill(), waybill);
		}
		
		@Test
		public void testGetWaybillWrongOperator() {
			this.testInitWaybillWaybillNotInDriverMission();
			assertNull(viewWaybillPageController.getWaybill());
		}
	}
	
	public static class OperatorTest {
		@Before
		public void setUp() throws InitializationError {
			ViewWaybillPageControllerTest.init();
			
			// set currentRole as Operator
			when(roleDao.findById(roleId)).thenReturn(user.getOperatorRole());
			
			viewWaybillPageController.initStrategy();
			RoleStrategy strategy = viewWaybillPageController.getStrategy();

			try {
				FieldUtils.writeField(strategy, "waybillDao", waybillDao, true);
			} catch (IllegalAccessException e) {
				throw new InitializationError(e);
			}
		}
		@Test
		public void testInitWaybill() {
			// waybill has the Operator user as operator
			waybill.setOperator(user);
			viewWaybillPageController.initWaybill();
		}

		@Test
		public void testInitWaybillWrongOperator() {
			// waybill has the wrong Operator user as operator
			waybill.setOperator(wrongUser);
			assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
				viewWaybillPageController.initWaybill();
			});
		}
		
		@Test
		public void testGetWaybill() {
			this.testInitWaybill();
			assertEquals(viewWaybillPageController.getWaybill(), waybill);
		}
		
		@Test
		public void testGetWaybillWrongOperator() {
			this.testInitWaybillWrongOperator();
			assertNull(viewWaybillPageController.getWaybill());
		}
	}

}
