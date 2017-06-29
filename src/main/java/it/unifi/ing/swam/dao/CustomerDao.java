package it.unifi.ing.swam.dao;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import it.unifi.ing.swam.model.Address;
import it.unifi.ing.swam.model.Customer;
import it.unifi.ing.swam.model.State;

public class CustomerDao extends BaseDao {
	public CustomerDao(){
        // FIXME - Codice da togliere!
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("swamproject");
        entityManager = emf.createEntityManager();
	}

    public Customer findById(Long id) {
        return entityManager.find(Customer.class, id);
    }
    
    public List<Customer> findByState(State state){
    	String query = "SELECT c FROM Customer c WHERE c.customer_state = :state";
        return entityManager.createQuery(query, Customer.class).setParameter("state", state)
                .getResultList();
    }
    
    public Customer findByOperatorId(Long id){
    	String query = "SELECT o FROM Operator o WHERE o.id = :id";
        return entityManager.createQuery(query, Customer.class).setParameter("id", id).getSingleResult();
    }
    
    public List<Customer> findByAddress(Address a){
    	String query = "SELECT ad FROM Address ad WHERE ad.street = :street AND ad.city = :city AND ad.address_state = :address_state AND ad.zip = :zip";
        return entityManager.createQuery(query, Customer.class).setParameter("street", a.getStreet()).setParameter("city", a.getCity()).setParameter("adress_state", a.getState()).setParameter("zip", a.getZip())
                .getResultList();
    }
    
    

}
