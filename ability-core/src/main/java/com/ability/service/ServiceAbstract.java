package com.ability.service;

import com.ability.model.BaseModel;

import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

/**
 * Created by ikizema on 15-08-21.
 */

@Interceptors(com.ability.service.interceptor.PerformanceLogging.class)
public class ServiceAbstract<T extends BaseModel> {

    public static final Logger LOG = Logger.getLogger(ServiceAbstract.class.getCanonicalName());

    protected final Class<T> classT;

    @PersistenceContext(unitName = "ability")
    protected EntityManager entityManager;

    public ServiceAbstract(Class<T> classT) {
        this.classT = classT;
    }

    public T save(T entity) {
        return entityManager.merge(entity);
    }

    public void remove(T entity) {
        entity = entityManager.merge(entity);
        entityManager.remove(entity);
    }

    public T getEntity(long id) {
        return entityManager.find(classT, id);
    }
}
