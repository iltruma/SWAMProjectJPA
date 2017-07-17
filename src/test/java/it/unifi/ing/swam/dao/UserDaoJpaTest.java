package it.unifi.ing.swam.dao;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
        user.setUsername("operator");
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
    public void testSaveThrowsIllegalArgumentException() {
        User userSave = ModelFactory.generateUser();

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            userDao.save(userSave);
        });
    }

    @Test
    public void testFindByEmail() {
        assertEquals(user, userDao.findByEmail(user.getEmail()));

        assertNull(userDao.findByEmail(new String("email2")));
    }

    @Test
    public void testFindByPhone() {
        assertEquals(user, userDao.findByPhone(user.getPhone()));

        assertNull(userDao.findByPhone(new String("phone2")));
    }

    @Test
    public void testFindByUsername() {
        assertEquals(user, userDao.findByUsername(user.getUsername()));

        assertNull(userDao.findByUsername(new String("username2")));
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

    @Test
    public void testFindByLoginInfo() {
        assertEquals(user, userDao.findByLoginInfo(user));
    }

    @Test
    public void testFindByLoginInfoFail() {
        assertNull(userDao.findByLoginInfo(ModelFactory.generateUser()));
    }

}
