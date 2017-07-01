package it.unifi.ing.swam.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.model.Agency;
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
        Agency agency = ModelFactory.generateAgency();
        user.setAgency(agency);
        user.addRole(ModelFactory.generateOperator());

        entityManager.persist(agency);
        entityManager.persist(user);

        userDao = new UserDao();
        JpaTest.inject(userDao, entityManager);
    }

    @Test
    public void testSave() {
        User userSave = ModelFactory.generateUser();
        userSave.addRole(ModelFactory.generateOperator());

        userDao.save(userSave);

        assertEquals(userSave, entityManager.createQuery("FROM User u WHERE u = :user", User.class)
                .setParameter("user", userSave).getSingleResult());
    }

    @Test
    public void testFindByEmail() {
        assertEquals(user, userDao.findByEmail(user.getEmail()));
    }

    @Test
    public void testFindByPhone() {
        assertEquals(user, userDao.findByPhone(user.getPhone()));
    }

    @Test
    public void testFindByUsername() {
        assertEquals(user, userDao.findByUsername(user.getUsername()));
    }

    @Test
    public void testFindByName() {
        List<User> result = userDao.findByName(user.getName());
        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }

    @Test
    public void testFindByAgencyId() {
        List<User> result = userDao.findByAgencyId(user.getAgency().getId());
        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }

}
