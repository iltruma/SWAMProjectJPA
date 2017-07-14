package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.Address;
import it.unifi.ing.swam.model.Customer;
import it.unifi.ing.swam.model.State;
import it.unifi.ing.swam.model.User;

public class CustomerDao extends BaseDao<Customer> {

    private static final long serialVersionUID = 17L;

    public CustomerDao() {
        super(Customer.class);
    }

    public List<Customer> findByCustomerState(State customer_state) {
        return entityManager.createQuery("SELECT c FROM Customer c WHERE c.state = :customer_state", Customer.class)
                .setParameter("customer_state", customer_state).getResultList();
    }

    @Deprecated
    public List<Customer> findByOperatorId(Long operator_id) {
        return entityManager.createQuery("FROM Customer WHERE operator_id = :operator_id", Customer.class)
                .setParameter("operator_id", operator_id).getResultList();
    }

    public List<Customer> findByOperator(User operator) {
        return entityManager.createQuery("SELECT c FROM Customer c WHERE c.operator = :operator", Customer.class)
                .setParameter("operator", operator).getResultList();
    }

    public List<Customer> findByAddress(Address address) {
        return entityManager.createQuery("SELECT c FROM Customer c WHERE c.address = :address ", Customer.class)
                .setParameter("address", address).getResultList();
    }

    @Deprecated
    public Customer findByUserId(Long ownerId) {
        List<Customer> result = entityManager.createQuery("FROM Customer WHERE owner_id = :owner_id", Customer.class)
                .setParameter("owner_id", ownerId).getResultList();
        if (!result.isEmpty())
            return result.get(0);
        return null;
    }

    public Customer findByUser(User owner) {
        List<Customer> result = entityManager
                .createQuery("SELECT c FROM Customer c WHERE c.owner = :owner", Customer.class)
                .setParameter("owner", owner).getResultList();
        if (!result.isEmpty())
            return result.get(0);
        return null;
    }

}
