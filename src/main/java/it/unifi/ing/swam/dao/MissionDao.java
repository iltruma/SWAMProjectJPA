package it.unifi.ing.swam.dao;

import java.util.Date;
import java.util.List;

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
            return entityManager.createQuery("SELECT m FROM Mission m WHERE m.driver = :driver", Mission.class)
                    .setParameter("driver", driver).getResultList();
        } else
            throw new IllegalArgumentException("The user is not a driver.");
    }

    public Mission findByDate(Date date) {
        return entityManager.createQuery("FROM Mission WHERE date = :date", Mission.class)
                .setParameter("date", date).getSingleResult();
    }

}
