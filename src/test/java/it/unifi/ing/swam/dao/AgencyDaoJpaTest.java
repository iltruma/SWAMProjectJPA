package it.unifi.ing.swam.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.model.Address;
import it.unifi.ing.swam.model.Agency;
import it.unifi.ing.swam.model.ModelFactory;

public class AgencyDaoJpaTest extends JpaTest {

    AgencyDao agencyDao;

    Agency agency;

    @Override
    protected void init() throws InitializationError {

        agency = ModelFactory.generateAgency();
        agency.setName("name");
        Address address = new Address();
        address.setStreet("street");
        address.setCity("city");
        address.setZip("zip");
        address.setState("state");
        agency.setAddress(address);

        entityManager.persist(agency);

        agencyDao = new AgencyDao();
        JpaTest.inject(agencyDao, entityManager);
    }

    @Test
    public void testSave() {
        Agency agencySave = ModelFactory.generateAgency();

        agencyDao.save(agencySave);

        assertEquals(agencySave, entityManager.createQuery("FROM Agency a WHERE a = :agency", Agency.class)
                .setParameter("agency", agencySave).getSingleResult());
    }

    @Test
    public void testFindByName() {
        assertEquals(agency, agencyDao.findByName(agency.getName()));

        assertNull(agencyDao.findByName(new String("name2")));
    }

    @Test
    public void testFindByAddress() {
        assertEquals(agency, agencyDao.findByAddress(agency.getAddress()));

        Address address = new Address();
        address.setStreet("street2");
        address.setCity("city2");
        address.setZip("zip2");
        address.setState("state2");
        assertNull(agencyDao.findByAddress(address));
    }

}
