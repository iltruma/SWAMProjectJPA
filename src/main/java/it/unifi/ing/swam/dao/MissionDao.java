package it.unifi.ing.swam.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.TemporalType;

import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.User;

public class MissionDao extends BaseDao<Mission> {

    private static final long serialVersionUID = 21L;

    public MissionDao() {
        super(Mission.class);
    }

    @Deprecated
    public List<Mission> findByDriverId(Long driver_id) {
        return entityManager.createQuery("FROM Mission WHERE driver_id = :driver_id", Mission.class)
                .setParameter("driver_id", driver_id).getResultList();
    }

    public List<Mission> findByDriver(User driver) {
        return entityManager.createQuery("FROM Mission m WHERE m.driver = :driver", Mission.class)
                .setParameter("driver", driver.getDriverRole()).getResultList();

    }

    public Mission findByDate(Calendar date) {
        List<Mission> result = entityManager.createQuery("FROM Mission WHERE date = :date", Mission.class)
                .setParameter("date", date, TemporalType.DATE).getResultList();
        if (!result.isEmpty())
            return result.get(0);
        return null;
    }

    // Da qui metodi per controller.

    public Mission findByDriverAndDate(User driver, Calendar date) {
        List<Mission> result = entityManager
                .createQuery("FROM Mission m WHERE m.driver = :driver AND m.date = :date", Mission.class)
                .setParameter("driver", driver.getDriverRole()).setParameter("date", date, TemporalType.DATE)
                .getResultList();
        if (!result.isEmpty())
            return result.get(0);
        return null;
    }

}
