package it.unifi.ing.swam.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unifi.ing.swam.model.BaseEntity;

public abstract class BaseDao {

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
