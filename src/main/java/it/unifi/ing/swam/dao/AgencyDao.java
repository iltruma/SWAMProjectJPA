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
    	String query = "SELECT ag FROM Agency ag WHERE ag.name = :name";
        return entityManager.createQuery(query, Agency.class).setParameter("name", name)
                .getSingleResult();
    }
    
    public Agency findByAddress(Address a){
    	String query = "SELECT ad FROM Address ad WHERE ad.street = :street AND ad.city = :city AND ad.address_state = :address_state AND ad.zip = :zip";
        return entityManager.createQuery(query, Agency.class).setParameter("street", a.getStreet()).setParameter("city", a.getCity()).setParameter("adress_state", a.getState()).setParameter("zip", a.getZip())
                .getSingleResult();
    }
	
}
