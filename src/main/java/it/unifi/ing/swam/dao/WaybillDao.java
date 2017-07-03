package it.unifi.ing.swam.dao;

import java.util.List;

import it.unifi.ing.swam.model.Agency;
import it.unifi.ing.swam.model.Receiver;
import it.unifi.ing.swam.model.RoleType;
import it.unifi.ing.swam.model.Tracking;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

public class WaybillDao extends BaseDao {

    public Waybill findById(Long id) {
        return entityManager.find(Waybill.class, id);
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

    public List<Waybill> findBySender(User sender) throws IllegalArgumentException {
        if (sender.hasRole(RoleType.CUSTOMER)) {
            return entityManager.createQuery("SELECT w FROM Waybill w WHERE w.sender = :sender", Waybill.class)
                    .setParameter("sender", sender).getResultList();
        } else
            throw new IllegalArgumentException("The user is not a customer.");
    }

    @Deprecated
    public List<Waybill> findByOperatorId(Long operatorId) {
        return entityManager.createQuery("FROM Waybill WHERE operator_id = :operator_id", Waybill.class)
                .setParameter("operator_id", operatorId).getResultList();
    }

    public List<Waybill> findByOperator(User operator) throws IllegalArgumentException {
        if (operator.hasRole(RoleType.OPERATOR)) {
            return entityManager.createQuery("SELECT w FROM Waybill w WHERE w.operator = :operator", Waybill.class)
                    .setParameter("operator", operator).getResultList();
        } else
            throw new IllegalArgumentException("The user is not an operator.");
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

    public List<Waybill> findProposedBySender(User sender) throws IllegalArgumentException {
        if (sender.hasRole(RoleType.CUSTOMER)) {
            return entityManager
                    .createQuery("SELECT w FROM Waybill w WHERE w.sender = :sender AND w.operator = :operator",
                            Waybill.class)
                    .setParameter("sender", sender).setParameter("operator", null).getResultList();
        } else
            throw new IllegalArgumentException("The user is not a customer.");
    }

    public List<Waybill> findValidatedBySender(User sender) throws IllegalArgumentException {
        if (sender.hasRole(RoleType.CUSTOMER)) {
            return entityManager
                    .createQuery("SELECT w FROM Waybill w WHERE w.sender = :sender AND w.operator != :operator",
                            Waybill.class)
                    .setParameter("sender", sender).setParameter("operator", null).getResultList();
        } else
            throw new IllegalArgumentException("The user is not a customer.");
    }

    public List<Waybill> findProposedByAgency(Agency agency) {
        return entityManager
                .createQuery("SELECT w FROM Waybill w WHERE w.sender.agency = :agency AND w.operator = :operator",
                        Waybill.class)
                .setParameter("agency", agency).setParameter("operator", null).getResultList();
    }

    public List<Waybill> findUnassignedToDriver(Agency agency) {
        return entityManager
                .createQuery("SELECT w FROM Waybill w WHERE w.sender.agency = :agency AND mission_id = :mission",
                        Waybill.class)
                .setParameter("agency", agency).setParameter("mission", null).getResultList();
    }

}
