package it.unifi.ing.swam.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.model.Driver;
import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;

public class MissionDaoJpaTest extends JpaTest {

    MissionDao missionDao;

    Mission mission;
    User user;

    @Override
    protected void init() throws InitializationError {

        Driver driver = ModelFactory.generateDriver();
        user = ModelFactory.generateUser();
        mission = ModelFactory.generateMission();
        Date date = new Date();
        mission.setDate(date);
        driver.addMission(mission);
        user.addRole(driver);

        entityManager.persist(user); // Test CASCADE

        missionDao = new MissionDao();
        JpaTest.inject(missionDao, entityManager);
    }

    @Test
    public void testSave() {
        Mission missionSave = ModelFactory.generateMission();

        missionDao.save(missionSave);

        assertEquals(missionSave, entityManager.createQuery("FROM Mission m WHERE m = :mission", Mission.class)
                .setParameter("mission", missionSave).getSingleResult());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFindByDriverId() {
        List<Mission> result = missionDao.findByDriverId(user.getRoles().get(0).getId());
        assertEquals(1, result.size());
        assertEquals(mission, result.get(0));
    }

    @Test
    public void testFindByDriver() {
        List<Mission> result = missionDao.findByDriver(user);
        assertEquals(1, result.size());
        assertEquals(mission, result.get(0));
    }

    @Test
    public void testFindByDriverThrowsIllegalArgumentException() {
        boolean thrown = false;

        try {
            User customer = ModelFactory.generateUser();
            customer.addRole(ModelFactory.generateCustomer());
            missionDao.findByDriver(customer);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void testFindByDate() {
        assertEquals(mission, missionDao.findByDate(mission.getDate()));
    }

}
