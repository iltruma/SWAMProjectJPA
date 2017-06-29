package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.Address;
import it.unifi.ing.swam.model.Customer;
import it.unifi.ing.swam.model.State;

public class CustomerDao extends BaseDao {

    public Customer findById(Long id) {
        return entityManager.find(Customer.class, id);
    }

    public List<Customer> findByCustomerState(State customer_state) {
        return entityManager
                .createQuery("SELECT c FROM Customer c WHERE c.customer_state = :customer_state", Customer.class)
                .setParameter("customer_state", customer_state).getResultList();
    }

    public List<Customer> findByOperatorId(Long operator_id) {
        return entityManager.createQuery("SELECT c FROM Customer c WHERE c.operator_id = :operator_id", Customer.class)
                .setParameter("operator_id", operator_id).getResultList();
    }

    public List<Customer> findByAddress(Address a) {
        return entityManager
                .createQuery(
                        "SELECT c FROM Customer c WHERE c.street = :street "
                                + "AND c.city = :city AND c.address_state = :address_state AND c.zip = :zip",
                        Customer.class)
                .setParameter("street", a.getStreet()).setParameter("city", a.getCity())
                .setParameter("adress_state", a.getState()).setParameter("zip", a.getZip()).getResultList();
    }

}
