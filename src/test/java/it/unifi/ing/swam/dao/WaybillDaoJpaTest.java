package it.unifi.ing.swam.dao;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.reflect.FieldUtils;
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



		waybillDao = new WaybillDao();
		try {
			FieldUtils.writeField(waybillDao, "entityManager", entityManager, true);
		} catch (IllegalAccessException e) {
			throw new InitializationError(e);
		}
	}

	@Test
	public void testSave() {
		Waybill w = ModelFactory.generateWaybill();
		w.setSender(sender);
		w.setOperator(operator);
		waybillDao.save(w);

		entityManager.getTransaction().commit();

		assertEquals(w, entityManager
				.createQuery("from Waybill w where w.sender_id = :sender AND w.operator_id = :operator", Waybill.class)
				.setParameter("sender", w.getSender().getId()).setParameter("operator", w.getOperator().getId())
				.getSingleResult());


	}

}
