package it.unifi.ing.swam.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

public class WaybillDaoJpaTest extends JpaTest {

    User sender;
    User operator;
    WaybillDao waybillDao;

    @Override
    protected void init() throws InitializationError {

        operator = ModelFactory.generateUser();
        operator.setUsername("Operator");
        operator.setPassword("password");
        operator.setName("name");
        operator.setPhone("phone");
        operator.setEmail("email");
        operator.addRole(ModelFactory.generateOperator());

        sender = ModelFactory.generateUser();
        sender.setUsername("Customer");
        sender.setPassword("password");
        sender.setName("name");
        sender.setPhone("phone");
        sender.setEmail("email");
        sender.addRole(ModelFactory.generateCustomer());

        entityManager.persist(sender);
        entityManager.persist(operator);

        waybillDao = new WaybillDao();
        JpaTest.inject(waybillDao, entityManager);

    }

    @Test
    public void testSave() {
        Waybill w = ModelFactory.generateWaybill();
        w.setSender(sender);
        w.setOperator(operator);
        waybillDao.save(w);

        assertEquals(w, entityManager.find(Waybill.class, w.getId()));

    }

}
