package it.unifi.ing.swam.dao;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import it.unifi.ing.swam.model.Address;
import it.unifi.ing.swam.model.Customer;
import it.unifi.ing.swam.model.Operator;

public class CustomerDao extends BaseDao {
	public CustomerDao(){
        // FIXME - Codice da togliere!
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("swamproject");
        entityManager = emf.createEntityManager();
	}

    public Customer findById(Long id) {
        return entityManager.find(Customer.class, id);
    }
    
    public List<Customer> findByState(String state){
    	String query = "SELECT c FROM Customer c WHERE c.customer_state = :state";
        return entityManager.createQuery(query, Customer.class).setParameter("state", state)
                .getResultList();
    }
    
    public Operator findByOperatorId(Long id){
    	String query = "SELECT o FROM Operator o WHERE o.id = :id";
        return entityManager.createQuery(query, Operator.class).setParameter("id", id).getSingleResult();
    }
    
    public List<Address> findByAddress(String street, String city, String address_state, String zip){
    	String query = "select ad from Address ad where ad.street = :street and ad.city = :city and ad.address_state = :address_state and ad.zip = :zip";
        return entityManager.createQuery(query, Address.class).setParameter("street", street).setParameter("city", city).setParameter("adress_state", address_state).setParameter("zip", zip)
                .getResultList();
    }
    
    

}
