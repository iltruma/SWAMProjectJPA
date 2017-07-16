package it.unifi.ing.swam.dao;

import java.util.List;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import it.unifi.ing.swam.model.Agency;
import it.unifi.ing.swam.model.Receiver;
import it.unifi.ing.swam.model.Tracking;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

public class WaybillDao extends BaseDao<Waybill> {

    private static final long serialVersionUID = 25L;

    public WaybillDao() {
        super(Waybill.class);
    }

    @Deprecated
    public List<Waybill> findByMissionId(Long missionId) {
        return entityManager.createQuery("FROM Waybill WHERE mission_id = :mission_id", Waybill.class)
                .setParameter("mission_id", missionId).getResultList();
    }

    @Deprecated
    public List<Waybill> findBySenderId(Long senderId) {
        return entityManager.createQuery("FROM Waybill WHERE sender_id = :sender_id", Waybill.class)
                .setParameter("sender_id", senderId).getResultList();
    }

    public List<Waybill> findBySender(User sender) {
        return entityManager.createQuery("SELECT w FROM Waybill w WHERE w.sender = :sender", Waybill.class)
                .setParameter("sender", sender).getResultList();
    }

    @Deprecated
    public List<Waybill> findByOperatorId(Long operatorId) {
        return entityManager.createQuery("FROM Waybill WHERE operator_id = :operator_id", Waybill.class)
                .setParameter("operator_id", operatorId).getResultList();
    }

    public List<Waybill> findByOperator(User operator) {
        return entityManager.createQuery("SELECT w FROM Waybill w WHERE w.operator = :operator", Waybill.class)
                .setParameter("operator", operator).getResultList();
    }

    @Deprecated
    public List<Waybill> findByDestinationAgencyId(Long agencyId) {
        return entityManager
                .createQuery("FROM Waybill WHERE destinationAgency_id = :destinationAgency_id", Waybill.class)
                .setParameter("destinationAgency_id", agencyId).getResultList();
    }

    public List<Waybill> findByDestinationAgency(Agency agency) {
        return entityManager
                .createQuery("SELECT w FROM Waybill w WHERE w.receiver.destinationAgency = :agency", Waybill.class)
                .setParameter("agency", agency).getResultList();
    }

    public List<Waybill> findByTracking(Tracking tracking) {
        return entityManager.createQuery("FROM Waybill WHERE tracking = :tracking", Waybill.class)
                .setParameter("tracking", tracking).getResultList();
    }

    /**
     * Usa solo nome e indirizzo in Receiver.
     */
    public List<Waybill> findByReceiver(Receiver receiver) {
        return entityManager
                .createQuery("SELECT w FROM Waybill w WHERE w.receiver.name = :name AND w.receiver.address = :address",
                        Waybill.class)
                .setParameter("name", receiver.getName()).setParameter("address", receiver.getAddress())
                .getResultList();
    }

    // Da qui metodi per controller.

    public List<Waybill> findProposedBySender(User sender) {
        return entityManager
                .createQuery("SELECT w FROM Waybill w WHERE w.sender = :sender AND w.operator IS NULL", Waybill.class)
                .setParameter("sender", sender).getResultList();
    }

    public List<Waybill> findValidatedBySender(User sender) {
        return entityManager.createQuery("SELECT w FROM Waybill w WHERE w.sender = :sender AND w.operator IS NOT NULL",
                Waybill.class).setParameter("sender", sender).getResultList();
    }

    public List<Waybill> findProposedByAgency(Agency agency) {
        return entityManager
                .createQuery("SELECT w FROM Waybill w WHERE w.sender.agency = :agency AND w.operator IS NULL",
                        Waybill.class)
                .setParameter("agency", agency).getResultList();
    }

    public List<Waybill> findUnassignedToDriver(Agency agency) {
        return entityManager
                .createQuery("SELECT w FROM Waybill w WHERE w.sender.agency = :agency AND mission_id IS NULL",
                        Waybill.class)
                .setParameter("agency", agency).getResultList();
    }

    public List<Waybill> advancedSearch(Waybill waybill, int maxResult) {
        String query = "SELECT w FROM Waybill w WHERE ";

        if (waybill.getAcceptDate() != null)
            query += "w.acceptDate = :acceptDate AND ";
        if (waybill.getDeliveryDate() != null)
            query += "w.deliveryDate = :deliveryDate AND ";
        if (waybill.getReceiver().getName() != null)
            query += "w.receiver.name = :name AND ";
        if (waybill.getReceiver().getAddress() != null)
            query += "w.receiver.address = :address AND ";
        if (waybill.getTracking() != null)
            query += "w.tracking = :tracking AND ";
        if (waybill.getOperator().getId() != null)
            query += "operator_id = :operator AND ";
        if (waybill.getSender().getId() != null)
            query += "sender_id = :sender AND ";

        TypedQuery<Waybill> q;

        if (waybill.getAcceptDate() == null && waybill.getDeliveryDate() == null
                && waybill.getReceiver().getName() == null && waybill.getReceiver().getAddress() == null
                && waybill.getTracking() == null && waybill.getOperator().getId() == null
                && waybill.getSender().getId() == null)
            q = entityManager.createQuery(query.substring(0, query.length() - 7), Waybill.class)
            .setMaxResults(maxResult);
        else
            q = entityManager.createQuery(query.substring(0, query.length() - 4), Waybill.class)
            .setMaxResults(maxResult);

        if (waybill.getAcceptDate() != null)
            q.setParameter("acceptDate", waybill.getAcceptDate(), TemporalType.DATE);
        if (waybill.getDeliveryDate() != null)
            q.setParameter("deliveryDate", waybill.getDeliveryDate(), TemporalType.DATE);
        if (waybill.getReceiver().getName() != null)
            q.setParameter("name", waybill.getReceiver().getName());
        if (waybill.getReceiver().getAddress() != null)
            q.setParameter("address", waybill.getReceiver().getAddress());
        if (waybill.getTracking() != null)
            q.setParameter("tracking", waybill.getTracking());
        if (waybill.getOperator().getId() != null)
            q.setParameter("operator", waybill.getOperator().getId());
        if (waybill.getSender().getId() != null)
            q.setParameter("sender", waybill.getSender().getId());

        return q.getResultList();
    }

}
