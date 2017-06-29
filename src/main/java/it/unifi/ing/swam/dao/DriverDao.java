package it.unifi.ing.swam.dao;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import it.unifi.ing.swam.model.Driver;

public class DriverDao extends BaseDao {

	public DriverDao() {
		// FIXME - Codice da togliere!
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("swamproject");
		entityManager = emf.createEntityManager();
	}

	public Driver findById(Long id) {
		return entityManager.find(Driver.class, id);
	}

	public Driver findByTruck(String brand, Float capacity, String model, Integer year, String type, Float volume) {
		String query = "SELECT d FROM Driver d WHERE d.brand = :brand AND d.capacity = :capacity AND d.model = :model AND d.registrationYear = :year AND d.type = :type AND d.volume = :volume";
		return entityManager.createQuery(query, Driver.class).setParameter("brand", brand)
				.setParameter("capacity", capacity).setParameter("model", model).setParameter("year", year)
				.setParameter("type", type).setParameter("volume", volume).getSingleResult();
	}

	public List<Driver> findByZone(String zone) {
		String query = "SELECT d FROM Driver d WHERE d.zone = :zone";
		return entityManager.createQuery(query, Driver.class).setParameter("zone", zone).getResultList();
	}

}
