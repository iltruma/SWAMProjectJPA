package it.unifi.ing.swam.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import it.unifi.ing.swam.model.Mission;

public class MissionDao extends BaseDao {

	public MissionDao(){
        // FIXME - Codice da togliere!
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("swamproject");
        entityManager = emf.createEntityManager();
	}

    public Mission findById(Long id) {
        return entityManager.find(Mission.class, id);
    }
	
    public List<Mission> findByDriverId(Long id) {
    	String query = "SELECT m FROM Mission m WHERE m.driver_id = :id";
        return entityManager.createQuery(query, Mission.class).setParameter("id", id)
                .getResultList();
    }
    
    public Mission findByDriverId(Date date) {
    	String query = "SELECT m FROM Mission m WHERE m.date = :date";
        return entityManager.createQuery(query, Mission.class).setParameter("date", date)
                .getSingleResult();
    }
    
}
