package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.Agency;
import it.unifi.ing.swam.model.Driver;
import it.unifi.ing.swam.model.Truck;
import it.unifi.ing.swam.model.User;

public class DriverDao extends BaseDao {

    public Driver findById(Long id) {
        return entityManager.find(Driver.class, id);
    }

    // TODO - Scegliere solo alcuni campi di Truck.
    public Driver findByTruck(Truck truck) {
        List<Driver> result = entityManager.createQuery("SELECT d FROM Driver d WHERE d.truck = :truck", Driver.class)
                .setParameter("truck", truck).getResultList();
        if (!result.isEmpty())
            return result.get(0);
        return null;
    }

    public List<Driver> findByZone(String zone) {
        return entityManager.createQuery("FROM Driver WHERE zone = :zone", Driver.class).setParameter("zone", zone)
                .getResultList();
    }

    @Deprecated
    public Driver findByUserId(Long ownerId) {
        List<Driver> result = entityManager.createQuery("FROM Driver WHERE owner_id = :owner_id", Driver.class)
                .setParameter("owner_id", ownerId).getResultList();
        if (!result.isEmpty())
            return result.get(0);
        return null;
    }

    public Driver findByUser(User owner) {
        List<Driver> result = entityManager.createQuery("SELECT d FROM Driver d WHERE d.owner = :owner", Driver.class)
                .setParameter("owner", owner).getResultList();
        if (!result.isEmpty())
            return result.get(0);
        return null;
    }

    public List<Driver> findAvailable(Agency agency) {
        return entityManager.createQuery("SELECT d FROM Driver d WHERE d.availType = :availType AND d.owner.agency = :agency", Driver.class)
                .setParameter("availType", Driver.AvailType.AVAILABLE).setParameter("agency", agency).getResultList();
    }

}
