package it.unifi.ing.swam.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.model.Driver;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.Operator;
import it.unifi.ing.swam.model.Truck;
import it.unifi.ing.swam.model.User;

public class DriverDaoJpaTest extends JpaTest {

    DriverDao driverDao;

    Driver driver;

    @Override
    protected void init() throws InitializationError {

        driver = ModelFactory.generateDriver();
        Truck truck = new Truck();
        driver.setTruck(truck);
        driver.setZone("zone");
        User user = ModelFactory.generateUser();
        user.addRole(driver);

        entityManager.persist(user); // Test CASCADE

        driverDao = new DriverDao();
        JpaTest.inject(driverDao, entityManager);
    }

    @Test
    public void testSave() {
        Operator driverSave = ModelFactory.generateOperator();

        driverDao.save(driverSave);

        assertEquals(driverSave, entityManager.createQuery("FROM Driver d WHERE d = :driver", Driver.class)
                .setParameter("driver", driverSave).getSingleResult());
    }

    public void testFindByTruck() {
        assertEquals(driver, driverDao.findByTruck(driver.getTruck()));
    }

    @Test
    public void testFindByZone() {
        List<Driver> result = driverDao.findByZone(driver.getZone());
        assertEquals(1, result.size());
        assertEquals(driver, result.get(0));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFindByUserId() {
        assertEquals(driver, driverDao.findByUserId(driver.getOwner().getId()));
    }

    @Test
    public void testFindByUser() {
        assertEquals(driver, driverDao.findByUser(driver.getOwner()));
    }
}