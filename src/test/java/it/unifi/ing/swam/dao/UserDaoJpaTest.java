package it.unifi.ing.swam.dao;

import static org.junit.Assert.assertEquals;
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
		JpaTest.inject(userDao, entityManager);

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
	
	@Test
	public void testSave1() {
		userDao.save(user);

		entityManager.getTransaction().commit();

		assertEquals(user, entityManager
				.createQuery("from User u where u = :user", User.class)
				.setParameter("user", user )
				.getSingleResult());


	}

}
