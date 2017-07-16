package it.unifi.ing.swam.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unifi.ing.swam.model.BaseEntity;

public abstract class BaseDao<E extends BaseEntity> implements Serializable {

    private static final long serialVersionUID = 15L;

    private final Class<E> type;

    @PersistenceContext
    protected EntityManager entityManager;

    protected BaseDao(Class<E> type) {
        this.type = type;
    }

    public E findById(Long typeId) {
        return entityManager.find(type, typeId);
    }

    public void save(E entity) {
        if (entity.getId() != null)
            entityManager.merge(entity);
        else
            entityManager.persist(entity);
    }

    public void delete(E entity) {
        if (entity.getId() != null)
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        else
            throw new IllegalArgumentException("Entity not persisted");
    }

}
