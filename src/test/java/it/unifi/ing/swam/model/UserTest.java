package it.unifi.ing.swam.model;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class UserTest {

	private static User operatorUser;
	private static User driverUser;
	private static User customerUser;

	private static User operatorCustomerUser;
	private static User operatorDriverUser;
	private static User driverCustomerUser;

	private static User operatorDriverCustomerUser;

	private static void init() {
		operatorUser = new User(UUID.randomUUID().toString());
		operatorUser.addRole(new Operator(UUID.randomUUID().toString()));

		driverUser = new User(UUID.randomUUID().toString());
		driverUser.addRole(new Driver(UUID.randomUUID().toString()));

		customerUser = new User(UUID.randomUUID().toString());
		customerUser.addRole(new Customer(UUID.randomUUID().toString()));

		operatorCustomerUser = new User(UUID.randomUUID().toString());
		operatorCustomerUser.addRole(new Operator(UUID.randomUUID().toString()));
		operatorCustomerUser.addRole(new Customer(UUID.randomUUID().toString()));

		operatorDriverUser = new User(UUID.randomUUID().toString());
		operatorDriverUser.addRole(new Operator(UUID.randomUUID().toString()));
		operatorDriverUser.addRole(new Driver(UUID.randomUUID().toString()));

		driverCustomerUser = new User(UUID.randomUUID().toString());
		driverCustomerUser.addRole(new Driver(UUID.randomUUID().toString()));
		driverCustomerUser.addRole(new Customer(UUID.randomUUID().toString()));

		operatorDriverCustomerUser = new User(UUID.randomUUID().toString());
		operatorDriverCustomerUser.addRole(new Operator(UUID.randomUUID().toString()));
		operatorDriverCustomerUser.addRole(new Driver(UUID.randomUUID().toString()));
		operatorDriverCustomerUser.addRole(new Customer(UUID.randomUUID().toString()));

	}

	@Before
	public void setUp() {
		init();
	}

	public static class HasRoleTest {

		@Before
		public void setUp() {
			UserTest.init();
		}

		@Test
		public void testHasRoleOperator() {
			RoleType roleToTest = RoleType.OPERATOR;
			assertEquals(operatorUser.hasRole(roleToTest), true);
			assertEquals(operatorDriverUser.hasRole(roleToTest), true);
			assertEquals(operatorCustomerUser.hasRole(roleToTest), true);
			assertEquals(operatorDriverCustomerUser.hasRole(roleToTest), true);

			assertEquals(driverUser.hasRole(roleToTest), false);
			assertEquals(customerUser.hasRole(roleToTest), false);
			assertEquals(driverCustomerUser.hasRole(roleToTest), false);
		}

		@Test
		public void testHasRoleDriver() {
			RoleType roleToTest = RoleType.DRIVER;
			assertEquals(driverUser.hasRole(roleToTest), true);
			assertEquals(operatorDriverUser.hasRole(roleToTest), true);
			assertEquals(driverCustomerUser.hasRole(roleToTest), true);
			assertEquals(operatorDriverCustomerUser.hasRole(roleToTest), true);

			assertEquals(operatorUser.hasRole(roleToTest), false);
			assertEquals(customerUser.hasRole(roleToTest), false);
			assertEquals(operatorCustomerUser.hasRole(roleToTest), false);
		}

		@Test
		public void testHasRoleCUSTOMER() {
			RoleType roleToTest = RoleType.CUSTOMER;
			assertEquals(customerUser.hasRole(roleToTest), true);
			assertEquals(operatorCustomerUser.hasRole(roleToTest), true);
			assertEquals(driverCustomerUser.hasRole(roleToTest), true);
			assertEquals(operatorDriverCustomerUser.hasRole(roleToTest), true);

			assertEquals(driverUser.hasRole(roleToTest), false);
			assertEquals(operatorUser.hasRole(roleToTest), false);
			assertEquals(operatorDriverUser.hasRole(roleToTest), false);
		}

	}

	public static class GetRolesMethodsTest {
		
		@Before
		public void setUp() {
			UserTest.init();
		}
		
		@Test
		public void testGetDriverRole() {
			assertEquals(driverUser.getDriverRole().getType(), RoleType.DRIVER);
			assertEquals(operatorDriverUser.getDriverRole().getType(), RoleType.DRIVER);
			assertEquals(driverCustomerUser.getDriverRole().getType(), RoleType.DRIVER);
			assertEquals(operatorDriverCustomerUser.getDriverRole().getType(), RoleType.DRIVER);

			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
				operatorUser.getDriverRole();
			});
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
				customerUser.getDriverRole();
			});
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
				operatorCustomerUser.getDriverRole();
			});
		}
		
		@Test
		public void testGetCustomerRole() {
			assertEquals(customerUser.getCustomerRole().getType(), RoleType.CUSTOMER);
			assertEquals(operatorCustomerUser.getCustomerRole().getType(), RoleType.CUSTOMER);
			assertEquals(driverCustomerUser.getCustomerRole().getType(), RoleType.CUSTOMER);
			assertEquals(operatorDriverCustomerUser.getCustomerRole().getType(), RoleType.CUSTOMER);

			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
				operatorUser.getCustomerRole();
			});
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
				driverUser.getCustomerRole();
			});
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
				operatorDriverUser.getCustomerRole();
			});
		}
		
		@Test
		public void testGetOperatorRole() {
			assertEquals(operatorUser.getOperatorRole().getType(), RoleType.OPERATOR);
			assertEquals(operatorCustomerUser.getOperatorRole().getType(), RoleType.OPERATOR);
			assertEquals(operatorDriverUser.getOperatorRole().getType(), RoleType.OPERATOR);
			assertEquals(operatorDriverCustomerUser.getOperatorRole().getType(), RoleType.OPERATOR);

			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
				customerUser.getOperatorRole();
			});
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
				driverUser.getOperatorRole();
			});
			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
				driverCustomerUser.getOperatorRole();
			});
		}
	}

}
