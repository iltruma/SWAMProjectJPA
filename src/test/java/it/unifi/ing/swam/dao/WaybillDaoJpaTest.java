package it.unifi.ing.swam.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.model.Address;
import it.unifi.ing.swam.model.Agency;
import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.Receiver;
import it.unifi.ing.swam.model.Tracking;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

public class WaybillDaoJpaTest extends JpaTest {

    WaybillDao waybillDao;

    Waybill waybill;
    Mission mission;

    Waybill proposedWaybill;
    Waybill validatedWaybill;
    Waybill unassignedWaybill;

    @Override
    protected void init() throws InitializationError {

        waybill = ModelFactory.generateWaybill();

        User operator = ModelFactory.generateUser();
        operator.addRole(ModelFactory.generateOperator());

        User sender = ModelFactory.generateUser();
        sender.addRole(ModelFactory.generateCustomer());
        Agency departureAgency = ModelFactory.generateAgency();
        sender.setAgency(departureAgency);

        mission = ModelFactory.generateMission();
        mission.addWaybill(waybill);

        Receiver receiver = new Receiver();
        Agency destinationAgency = ModelFactory.generateAgency();
        receiver.setDestinationAgency(destinationAgency);
        receiver.setName("name");
        receiver.setPhone("055055055");
        receiver.setEmail("receiver@unifi.it");
        Address address = new Address("street", "city", "state", "zip");
        receiver.setAddress(address);

        Calendar date = Calendar.getInstance();
        date.setTime(new Date(53264723543L));
        waybill.setAcceptDate(date);
        waybill.setDeliveryDate(date);
        waybill.setOperator(operator);
        waybill.setSender(sender);
        waybill.setReceiver(receiver);
        waybill.setTracking(Tracking.SHIPPING);

        entityManager.persist(sender);
        entityManager.persist(departureAgency);
        entityManager.persist(operator);
        entityManager.persist(destinationAgency);
        entityManager.persist(waybill);
        entityManager.persist(mission);

        proposedWaybill = ModelFactory.generateWaybill();
        User otherSender = ModelFactory.generateUser();
        otherSender.addRole(ModelFactory.generateCustomer());
        otherSender.setAgency(departureAgency);
        proposedWaybill.setSender(otherSender);

        validatedWaybill = ModelFactory.generateWaybill();
        User justAnotherSender = ModelFactory.generateUser();
        justAnotherSender.addRole(ModelFactory.generateCustomer());
        justAnotherSender.setAgency(destinationAgency);
        validatedWaybill.setSender(justAnotherSender);
        User otherOperator = ModelFactory.generateUser();
        otherOperator.addRole(ModelFactory.generateOperator());
        validatedWaybill.setOperator(otherOperator);

        entityManager.persist(proposedWaybill);
        entityManager.persist(otherSender);
        entityManager.persist(justAnotherSender);
        entityManager.persist(otherOperator);
        entityManager.persist(validatedWaybill);

        waybillDao = new WaybillDao();
        JpaTest.inject(waybillDao, entityManager);
    }

    @Test
    public void testSave() {
        Waybill waybillSave = ModelFactory.generateWaybill();

        waybillDao.save(waybillSave);

        assertEquals(waybillSave, entityManager.find(Waybill.class, waybillSave.getId()));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFindByMissionId() {
        List<Waybill> result = waybillDao.findByMissionId(mission.getId());
        assertEquals(1, result.size());
        assertEquals(waybill, result.get(0));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFindBySenderId() {
        List<Waybill> result = waybillDao.findBySenderId(waybill.getSender().getId());
        assertEquals(1, result.size());
        assertEquals(waybill, result.get(0));
    }

    @Test
    public void testFindBySender() {
        List<Waybill> result = waybillDao.findBySender(waybill.getSender());
        assertEquals(1, result.size());
        assertEquals(waybill, result.get(0));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFindByOperatorId() {
        List<Waybill> result = waybillDao.findByOperatorId(waybill.getOperator().getId());
        assertEquals(1, result.size());
        assertEquals(waybill, result.get(0));
    }

    @Test
    public void testFindByOperator() {
        List<Waybill> result = waybillDao.findByOperator(waybill.getOperator());
        assertEquals(1, result.size());
        assertEquals(waybill, result.get(0));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFindByDestinationAgencyId() {
        List<Waybill> result = waybillDao
                .findByDestinationAgencyId(waybill.getReceiver().getDestinationAgency().getId());
        assertEquals(1, result.size());
        assertEquals(waybill, result.get(0));
    }

    @Test
    public void testFindByDestinationAgency() {
        List<Waybill> result = waybillDao.findByDestinationAgency(waybill.getReceiver().getDestinationAgency());
        assertEquals(1, result.size());
        assertEquals(waybill, result.get(0));
    }

    @Test
    public void testFindByTracking() {
        List<Waybill> result = waybillDao.findByTracking(waybill.getTracking());
        assertEquals(1, result.size());
        assertEquals(waybill, result.get(0));
    }

    @Test
    public void testFindByReceiver() {
        List<Waybill> result = waybillDao.findByReceiver(waybill.getReceiver());
        assertEquals(1, result.size());
        assertEquals(waybill, result.get(0));
    }

    // Da qui metodi per controller.

    @Test
    public void testFindProposedBySender() {
        List<Waybill> result = waybillDao.findProposedBySender(proposedWaybill.getSender());
        assertEquals(1, result.size());
        assertEquals(proposedWaybill, result.get(0));

        result = waybillDao.findProposedBySender(validatedWaybill.getSender());
        assertEquals(0, result.size());
    }

    @Test
    public void testFindValidatedBySender() {
        List<Waybill> result = waybillDao.findValidatedBySender(validatedWaybill.getSender());
        assertEquals(1, result.size());
        assertEquals(validatedWaybill, result.get(0));

        result = waybillDao.findValidatedBySender(proposedWaybill.getSender());
        assertEquals(0, result.size());
    }

    @Test
    public void testFindProposedByAgency() {
        List<Waybill> result = waybillDao.findProposedByAgency(proposedWaybill.getSender().getAgency());
        assertEquals(1, result.size());
        assertEquals(proposedWaybill, result.get(0));
    }

    @Test
    public void testUnassignedToDriver() {
        List<Waybill> result = waybillDao.findUnassignedToDriver(validatedWaybill.getSender().getAgency());
        assertEquals(1, result.size());
        assertEquals(validatedWaybill, result.get(0));

        result = waybillDao.findUnassignedToDriver(proposedWaybill.getSender().getAgency());
        assertEquals(0, result.size());
    }

    @Test
    public void testAdvancedSearch() {
        List<Waybill> result = waybillDao.advancedSearch(waybill, 10);
        assertEquals(1, result.size());
        assertEquals(waybill, result.get(0));

        Waybill emptyWaybill = ModelFactory.generateWaybill();
        emptyWaybill.setTracking(null);
        User operator = ModelFactory.generateUser();
        operator.addRole(ModelFactory.generateOperator());
        User sender = ModelFactory.generateUser();
        sender.addRole(ModelFactory.generateCustomer());
        Receiver receiver = new Receiver();
        Agency destinationAgency = ModelFactory.generateAgency();
        receiver.setDestinationAgency(destinationAgency);

        emptyWaybill.setReceiver(receiver);
        emptyWaybill.setOperator(operator);
        emptyWaybill.setSender(sender);

        List<Waybill> resultList = new ArrayList<>();
        resultList.add(waybill);
        resultList.add(proposedWaybill);
        resultList.add(validatedWaybill);

        assertEquals(resultList, waybillDao.advancedSearch(emptyWaybill, 10));

    }

}
