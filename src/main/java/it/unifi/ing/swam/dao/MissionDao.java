package it.unifi.ing.swam.dao;

import java.util.Date;
import java.util.List;

import it.unifi.ing.swam.model.Mission;

public class MissionDao extends BaseDao {

    public Mission findById(Long id) {
        return entityManager.find(Mission.class, id);
    }

    public List<Mission> findByDriverId(Long driver_id) {
        return entityManager.createQuery("SELECT m FROM Mission m WHERE m.driver_id = :driver_id", Mission.class)
                .setParameter("driver_id", driver_id).getResultList();
    }

    public Mission findByDate(Date date) {
        return entityManager.createQuery("SELECT m FROM Mission m WHERE m.date = :date", Mission.class)
                .setParameter("date", date).getSingleResult();
    }

}
