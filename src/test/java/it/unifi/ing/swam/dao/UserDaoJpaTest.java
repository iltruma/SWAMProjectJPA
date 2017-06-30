package it.unifi.ing.swam.dao;

import static org.junit.Assert.assertEquals;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;

public class UserDaoJpaTest extends JpaTest {

	UserDao userDao;
	
	User user;



	@Override
	protected void init() throws InitializationError {

        user = ModelFactory.generateUser();
        user.setUsername("Operator");
        user.setPassword("password");
        user.setName("name");
        user.setPhone("phone");
        user.setEmail("email");
        user.addRole(ModelFactory.generateOperator());



		userDao = new UserDao();
		try {
			FieldUtils.writeField(userDao, "entityManager", entityManager, true);
		} catch (IllegalAccessException e) {
			throw new InitializationError(e);
		}
	}

	@Test
	public void testSave() {
		userDao.save(user);

		entityManager.getTransaction().commit();

		assertEquals(user, entityManager
				.createQuery("from User u where u = :user", User.class)
				.setParameter("user", user )
				.getSingleResult());


	}

}