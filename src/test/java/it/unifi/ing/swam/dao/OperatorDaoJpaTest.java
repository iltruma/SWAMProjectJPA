package it.unifi.ing.swam.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.Operator;
import it.unifi.ing.swam.model.User;

public class OperatorDaoJpaTest extends JpaTest {

    OperatorDao operatorDao;

    Operator operator;
    User otherUser;

    @Override
    protected void init() throws InitializationError {

        operator = ModelFactory.generateOperator();
        User user = ModelFactory.generateUser();
        user.addRole(operator);

        otherUser = ModelFactory.generateUser();

        entityManager.persist(user); // Test CASCADE
        entityManager.persist(otherUser);

        operatorDao = new OperatorDao();
        JpaTest.inject(operatorDao, entityManager);
    }

    @Test
    public void testSave() {
        Operator operatorSave = ModelFactory.generateOperator();

        operatorDao.save(operatorSave);

        assertEquals(operatorSave, entityManager.createQuery("FROM Operator o WHERE o = :operator", Operator.class)
                .setParameter("operator", operatorSave).getSingleResult());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFindByUserId() {
        assertEquals(operator, operatorDao.findByUserId(operator.getOwner().getId()));

        assertNull(operatorDao.findByUserId(operator.getId()));
    }

    @Test
    public void testFindByUser() {
        assertEquals(operator, operatorDao.findByUser(operator.getOwner()));

        assertNull(operatorDao.findByUser(otherUser));
    }
}
