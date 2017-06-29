package it.unifi.ing.swam.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import it.unifi.ing.swam.model.Address;
import it.unifi.ing.swam.model.Agency;

public class AgencyDao extends BaseDao {

	public AgencyDao(){
        // FIXME - Codice da togliere!
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("swamproject");
        entityManager = emf.createEntityManager();
	}
	
    public Agency findById(Long id) {
        return entityManager.find(Agency.class, id);
    }

    public Agency findByName(String name){
    	String query = "select ag from Agency ag where ag.name = :name";
        return entityManager.createQuery(query, Agency.class).setParameter("name", name)
                .getSingleResult();
    }
    
    public Address findByAddress(String street, String city, String address_state, String zip){
    	String query = "select ad from Address ad where ad.street = :street and ad.city = :city and ad.address_state = :address_state and ad.zip = :zip";
        return entityManager.createQuery(query, Address.class).setParameter("street", street).setParameter("city", city).setParameter("adress_state", address_state).setParameter("zip", zip)
                .getSingleResult();
    }
	
}
