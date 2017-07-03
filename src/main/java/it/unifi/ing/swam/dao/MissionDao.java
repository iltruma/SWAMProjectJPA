package it.unifi.ing.swam.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.TemporalType;

import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.RoleType;
import it.unifi.ing.swam.model.User;

public class MissionDao extends BaseDao {

    public Mission findById(Long id) {
        return entityManager.find(Mission.class, id);
    }

    @Deprecated
    public List<Mission> findByDriverId(Long driver_id) {
        return entityManager.createQuery("FROM Mission WHERE driver_id = :driver_id", Mission.class)
                .setParameter("driver_id", driver_id).getResultList();
    }

    public List<Mission> findByDriver(User driver) throws IllegalArgumentException {
        if (driver.hasRole(RoleType.DRIVER)) {
            return entityManager.createQuery("FROM Mission WHERE driver_id = :driver_id", Mission.class)
                    .setParameter("driver_id", driver.getDriverRole().getId()).getResultList();
        } else
            throw new IllegalArgumentException("The user is not a driver.");
    }

    public Mission findByDate(Calendar date) {
        List<Mission> result = entityManager.createQuery("FROM Mission WHERE date = :date", Mission.class)
                .setParameter("date", date, TemporalType.DATE).getResultList();
        if (!result.isEmpty())
            return result.get(0);
        return null;
    }

    // Da qui metodi per controller.

    public Mission findByDriverAndDate(User driver, Calendar date) throws IllegalArgumentException {
        if (driver.hasRole(RoleType.DRIVER)) {
            List<Mission> result = entityManager
                    .createQuery("FROM Mission WHERE driver_id = :driver_id AND date = :date", Mission.class)
                    .setParameter("driver_id", driver.getDriverRole().getId())
                    .setParameter("date", date, TemporalType.DATE).getResultList();
            if (!result.isEmpty())
                return result.get(0);
            return null;
        } else
            throw new IllegalArgumentException("The user is not a driver.");
    }

}
