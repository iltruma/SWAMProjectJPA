package it.unifi.ing.swam.dao;

import java.util.Date;
import java.util.List;

import it.unifi.ing.swam.model.BaseEntity;
import it.unifi.ing.swam.model.Receiver;
import it.unifi.ing.swam.model.Tracking;
import it.unifi.ing.swam.model.Waybill;

public class WaybillDao extends BaseDao {

    public Waybill findById(Long id) {
        return entityManager.find(Waybill.class, id);
    }

    public List<Waybill> findByMissionId(Long missionId) {
        return entityManager.createQuery("SELECT w FROM Waybill w WHERE w.mission_id = :mission_id", Waybill.class)
                .setParameter("mission_id", missionId).getResultList();
    }

    public List<Waybill> findBySenderId(Long senderId) {
        return entityManager.createQuery("SELECT w FROM Waybill w WHERE w.sender_id = :sender_id", Waybill.class)
                .setParameter("sender_id", senderId).getResultList();
    }

    public List<Waybill> findByOperatorId(Long operatorId) {
        return entityManager.createQuery("SELECT w FROM Waybill w WHERE w.operator_id = :operator_id", Waybill.class)
                .setParameter("operator_id", operatorId).getResultList();
    }

    public List<Waybill> findByDestinationAgencyId(Long agencyId) {
        return entityManager.createQuery("SELECT w FROM Waybill w WHERE w.destinationAgency_id = :destinationAgency_id",
                Waybill.class).setParameter("destinationAgency_id", agencyId).getResultList();
    }

    public List<Waybill> findByTracking(Tracking tracking) {
        return entityManager.createQuery("SELECT w FROM Waybill w WHERE w.tracking = :tracking", Waybill.class)
                .setParameter("tracking", tracking).getResultList();
    }

    public List<Waybill> findByDeliveryDate(Date deliveryDate) {
        return entityManager.createQuery("SELECT w FROM Waybill w WHERE w.deliveryDate = :deliveryDate", Waybill.class)
                .setParameter("deliveryDate", deliveryDate).getResultList();
    }

    /**
     * Usa solo nome e indirizzo in Receiver.
     */
    public List<Waybill> findByReceiver(Receiver receiver) {
        String query = "FROM Waybill w WHERE w.name = :name AND w.street = :street AND w.city = :city "
                + "AND w.zip = :zip AND w.address_state = :address_state";
        return entityManager.createQuery(query, Waybill.class).setParameter("name", receiver.getName())
                .setParameter("street", receiver.getAddress().getStreet())
                .setParameter("zip", receiver.getAddress().getZip())
                .setParameter("address_state", receiver.getAddress().getState()).getResultList();
    }
    
    public void save(Waybill entity) {
        if(entity.getId() != null) {
            entityManager.merge(entity);
        } else {
            entityManager.persist(entity);
        }
    }

}
