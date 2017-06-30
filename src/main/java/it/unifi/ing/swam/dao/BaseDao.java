package it.unifi.ing.swam.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import it.unifi.ing.swam.model.BaseEntity;

public abstract class BaseDao {
	
	BaseDao () {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("swamproject");
		entityManager = emf.createEntityManager();
	}

    @PersistenceContext
    protected EntityManager entityManager;

    public void save(BaseEntity entity) {
        if(entity.getId() != null) {
            entityManager.merge(entity);
        } else {
            entityManager.persist(entity);
        }
    }

}
