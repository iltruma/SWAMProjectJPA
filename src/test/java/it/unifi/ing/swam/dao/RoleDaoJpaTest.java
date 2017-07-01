package it.unifi.ing.swam.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.Role;
import it.unifi.ing.swam.model.User;

public class RoleDaoJpaTest extends JpaTest {

    RoleDao roleDao;

    Role operator;

    @Override
    protected void init() throws InitializationError {

        operator = ModelFactory.generateOperator();
        User user = ModelFactory.generateUser();
        operator.setOwner(user);

        entityManager.persist(user); //Test CASCADE

        roleDao = new RoleDao();
        JpaTest.inject(roleDao, entityManager);
    }

    @Test
    public void testSave() {
        Role roleSave = ModelFactory.generateDriver();

        roleDao.save(roleSave);

        assertEquals(roleSave, entityManager.createQuery("FROM Driver d WHERE d = :role", Role.class)
                .setParameter("role", roleSave).getSingleResult());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFindByUserId() {
        List<Role> result = roleDao.findByUserId(operator.getOwner().getId());
        assertEquals(1, result.size());
        assertEquals(operator, result.get(0));
    }

    @Test
    public void testFindByUser() {
        List<Role> result = roleDao.findByUser(operator.getOwner());
        assertEquals(1, result.size());
        assertEquals(operator, result.get(0));
    }
}
