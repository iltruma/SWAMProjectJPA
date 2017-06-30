package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.Driver;
import it.unifi.ing.swam.model.Truck;
import it.unifi.ing.swam.model.User;

public class DriverDao extends BaseDao {

    public Driver findById(Long id) {
        return entityManager.find(Driver.class, id);
    }

    // TODO - Scegliere solo alcuni campi di Truck.
    public Driver findByTruck(Truck truck) {
        return entityManager.createQuery("SELECT d FROM Driver d WHERE d.truck = :truck", Driver.class)
                .setParameter("truck", truck).getSingleResult();
    }

    public List<Driver> findByZone(String zone) {
        return entityManager.createQuery("FROM Driver WHERE zone = :zone", Driver.class).setParameter("zone", zone)
                .getResultList();
    }

    @Deprecated
    public Driver findByUserId(Long ownerId) {
        return entityManager.createQuery("FROM Driver WHERE owner_id = :owner_id", Driver.class)
                .setParameter("owner_id", ownerId).getSingleResult();
    }

    public Driver findByUser(User owner) {
        return entityManager.createQuery("SELECT d FROM Driver d WHERE d.owner = :owner", Driver.class)
                .setParameter("owner", owner).getSingleResult();
    }

}
