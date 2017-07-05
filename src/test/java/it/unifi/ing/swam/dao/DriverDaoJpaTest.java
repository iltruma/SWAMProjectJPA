package it.unifi.ing.swam.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.model.Agency;
import it.unifi.ing.swam.model.Driver;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.Truck;
import it.unifi.ing.swam.model.TruckType;
import it.unifi.ing.swam.model.User;

public class DriverDaoJpaTest extends JpaTest {

    DriverDao driverDao;

    Driver driver;
    User otherUser;
    
    Agency rightAgency;
    Agency wrongAgency; 

    @Override
    protected void init() throws InitializationError {

        driver = ModelFactory.generateDriver();
        Truck truck = new Truck();
        truck.setBrand("brand");
        truck.setCapacity(Float.valueOf(10F));
        truck.setModel("model");
        truck.setRegistrationYear(2017);
        truck.setVolume(Float.valueOf(10F));
        truck.setType(TruckType.VAN);
        driver.setTruck(truck);
        driver.setZone("zone");
        User user = ModelFactory.generateUser();
        user.addRole(driver);

        otherUser = ModelFactory.generateUser();
        
        rightAgency = ModelFactory.generateAgency();
        wrongAgency = ModelFactory.generateAgency();
        driver.getOwner().setAgency(rightAgency);

        entityManager.persist(rightAgency);
        entityManager.persist(wrongAgency);

        entityManager.persist(user); // Test CASCADE
        entityManager.persist(otherUser);

        driverDao = new DriverDao();
        JpaTest.inject(driverDao, entityManager);
    }

    @Test
    public void testSave() {
        Driver driverSave = ModelFactory.generateDriver();

        driverDao.save(driverSave);

        assertEquals(driverSave, entityManager.createQuery("FROM Driver d WHERE d = :driver", Driver.class)
                .setParameter("driver", driverSave).getSingleResult());
    }

    @Test
    public void testFindByTruck() {
        assertEquals(driver, driverDao.findByTruck(driver.getTruck()));

        assertNull(driverDao.findByTruck(new Truck()));
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

        assertNull(driverDao.findByUserId(driver.getId()));
    }

    @Test
    public void testFindByUser() {
        assertEquals(driver, driverDao.findByUser(driver.getOwner()));

        assertNull(driverDao.findByUser(otherUser));
    }
    
    @Test
    public void testFindAvailable() {
        assertEquals(1, driverDao.findAvailable(rightAgency).size());
        assertEquals(driver, driverDao.findAvailable(rightAgency).get(0));
        
        assertEquals(0, driverDao.findAvailable(wrongAgency).size());
    }
}
