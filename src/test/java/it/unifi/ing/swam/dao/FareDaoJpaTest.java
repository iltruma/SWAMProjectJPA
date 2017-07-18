package it.unifi.ing.swam.dao;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.NoResultException;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.model.Fare;
import it.unifi.ing.swam.model.ModelFactory;

public class FareDaoJpaTest extends JpaTest {

    FareDao fareDao;

    Fare fare;

    @Override
    protected void init() throws InitializationError {

        fare = ModelFactory.generateFare();
        fare.setFunctionCost("functionCost");
        fare.setZone("zone");
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(new Date(4326542837L));
        fare.setStartDate(startDate);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(new Date(5637146839L));
        fare.setEndDate(endDate);

        entityManager.persist(fare);

        fareDao = new FareDao();
        JpaTest.inject(fareDao, entityManager);
    }

    @Test
    public void testDelete() {

        Fare deleteFare = entityManager.createQuery("FROM Fare f WHERE f = :fare", Fare.class)
                .setParameter("fare", fare).getSingleResult();

        fareDao.delete(deleteFare);

        assertThatExceptionOfType(NoResultException.class).isThrownBy(() -> {
            entityManager.createQuery("FROM Fare f WHERE f = :fare", Fare.class).setParameter("fare", deleteFare)
                    .getSingleResult();
        });
    }

    @Test
    public void testDeleteThrowsIllegalArgumentException() {

        Fare deleteFare = ModelFactory.generateFare();

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            fareDao.delete(deleteFare);
        });
    }

    @Test
    public void testFindUnassignedToCustomer() {

        assertTrue(fareDao.isUnassignedToCustomer(fare));
    }

}
