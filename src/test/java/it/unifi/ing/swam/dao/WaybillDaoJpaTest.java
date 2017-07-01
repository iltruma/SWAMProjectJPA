package it.unifi.ing.swam.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Override
    protected void init() throws InitializationError {

        waybill = ModelFactory.generateWaybill();

        User operator = ModelFactory.generateUser();
        operator.addRole(ModelFactory.generateOperator());

        User sender = ModelFactory.generateUser();
        sender.addRole(ModelFactory.generateCustomer());

        mission = ModelFactory.generateMission();
        mission.addWaybill(waybill);

        Receiver receiver = new Receiver();
        Agency agency = ModelFactory.generateAgency();
        receiver.setDestinationAgency(agency);
        receiver.setName("name");
        Address address = new Address();
        address.setStreet("street");
        address.setCity("city");
        address.setZip("zip");
        address.setState("state");
        receiver.setAddress(address);

        waybill.setOperator(operator);
        waybill.setSender(sender);
        waybill.setReceiver(receiver);
        waybill.setTracking(Tracking.SHIPPING);
        Date date = new Date();
        date.setTime(1000000L);
        waybill.setDeliveryDate(date);

        entityManager.persist(sender);
        entityManager.persist(operator);
        entityManager.persist(agency);
        entityManager.persist(waybill);
        entityManager.persist(mission);

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
    public void testFindBySenderThrowsIllegalArgumentException() {
        boolean thrown = false;

        try {
            User driver = ModelFactory.generateUser();
            driver.addRole(ModelFactory.generateDriver());
            waybillDao.findBySender(driver);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }

        assertTrue(thrown);
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
    public void testFindByOperatorThrowsIllegalArgumentException() {
        boolean thrown = false;

        try {
            User driver = ModelFactory.generateUser();
            driver.addRole(ModelFactory.generateDriver());
            waybillDao.findByOperator(driver);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }

        assertTrue(thrown);
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
    public void testFindByDeliveryDate() {
        List<Waybill> result = waybillDao.findByDeliveryDate(waybill.getDeliveryDate());
        assertEquals(1, result.size());
        assertEquals(waybill, result.get(0));
    }

    @Test
    public void testFindByReceiver() {
        List<Waybill> result = waybillDao.findByReceiver(waybill.getReceiver());
        assertEquals(1, result.size());
        assertEquals(waybill, result.get(0));
    }

}
