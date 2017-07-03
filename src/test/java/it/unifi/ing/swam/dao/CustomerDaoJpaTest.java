package it.unifi.ing.swam.dao;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.model.Address;
import it.unifi.ing.swam.model.Customer;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.State;
import it.unifi.ing.swam.model.User;

public class CustomerDaoJpaTest extends JpaTest {

    CustomerDao customerDao;

    Customer customer;

    @Override
    protected void init() throws InitializationError {

        customer = ModelFactory.generateCustomer();
        Address address = new Address();
        address.setStreet("street");
        address.setCity("city");
        address.setZip("zip");
        address.setState("state");
        customer.setAddress(address);
        customer.setState(State.ACTIVE);
        User operator = ModelFactory.generateUser();
        operator.addRole(ModelFactory.generateOperator());
        customer.setOperator(operator);
        User user = ModelFactory.generateUser();
        user.addRole(customer);

        entityManager.persist(operator);
        entityManager.persist(user); // Test CASCADE

        customerDao = new CustomerDao();
        JpaTest.inject(customerDao, entityManager);
    }

    @Test
    public void testSave() {
        Customer customerSave = ModelFactory.generateCustomer();

        customerDao.save(customerSave);

        assertEquals(customerSave, entityManager.createQuery("FROM Customer c WHERE c = :customer", Customer.class)
                .setParameter("customer", customerSave).getSingleResult());
    }

    @Test
    public void testFindByAddress() {
        List<Customer> result = customerDao.findByAddress(customer.getAddress());
        assertEquals(1, result.size());
        assertEquals(customer, result.get(0));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFindByOperatorId() {
        List<Customer> result = customerDao.findByOperatorId(customer.getOperator().getId());
        assertEquals(1, result.size());
        assertEquals(customer, result.get(0));
    }

    @Test
    public void testFindByOperator() {
        List<Customer> result = customerDao.findByOperator(customer.getOperator());
        assertEquals(1, result.size());
        assertEquals(customer, result.get(0));
    }

    @Test
    public void testFindByOperatorThrowsIllegalArgumentException() {
        User customer = ModelFactory.generateUser();
        customer.addRole(ModelFactory.generateDriver());

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            customerDao.findByOperator(customer);
        });
    }

    @Test
    public void testFindByCustomerState() {
        List<Customer> result = customerDao.findByCustomerState(customer.getState());
        assertEquals(1, result.size());
        assertEquals(customer, result.get(0));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFindByUserId() {
        assertEquals(customer, customerDao.findByUserId(customer.getOwner().getId()));
    }

    @Test
    public void testFindByUser() {
        assertEquals(customer, customerDao.findByUser(customer.getOwner()));
    }
}
