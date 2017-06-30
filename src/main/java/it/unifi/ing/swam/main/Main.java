package it.unifi.ing.swam.main;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.lang3.reflect.FieldUtils;

import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

public class Main {

    public static void main(String[] args) throws IllegalAccessException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("swamproject");
        EntityManager em = emf.createEntityManager();

        WaybillDao waybillDao = new WaybillDao();
        FieldUtils.writeField(waybillDao, "entityManager", em, true);

        UserDao userDao = new UserDao();
        FieldUtils.writeField(userDao, "entityManager", em, true);

        User operator = ModelFactory.generateUser();
        operator.setUsername("Operator");
        operator.setPassword("password");
        operator.setName("name");
        operator.setPhone("phone");
        operator.setEmail("email");
        operator.addRole(ModelFactory.generateOperator());

        User sender = ModelFactory.generateUser();
        sender.setUsername("Customer");
        sender.setPassword("password");
        sender.setName("name");
        sender.setPhone("phone");
        sender.setEmail("email");
        sender.addRole(ModelFactory.generateCustomer());

        Waybill w = ModelFactory.generateWaybill();
        w.setSender(sender);
        w.setOperator(operator);

        em.getTransaction().begin();

        userDao.save(sender);
        userDao.save(operator);

        waybillDao.save(w);

        em.getTransaction().commit();

        System.out.println("fine dei save");

        List<Waybill> bolle = waybillDao.findByOperatorId(userDao.findByUsername("Operator").getId());

        for (Waybill b : bolle) {
            System.out.println(b.getId().toString());
        }

        System.out.println("Doveva venire 5");


        em.close();
        emf.close();

        System.exit(0);
    }

}
