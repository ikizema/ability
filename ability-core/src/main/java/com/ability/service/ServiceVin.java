package com.ability.service;

import com.ability.model.Vin;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by ikizema on 15-08-26.
 */
@Stateless
@LocalBean
public class ServiceVin extends ServiceAbstract<Vin> {
    private static final Logger logger = Logger.getLogger(ServiceVin.class.getCanonicalName());

    public ServiceVin() {
        super(Vin.class);
    }

    public Vin getVin(long id) {
        return entityManager.find(Vin.class, id);
    }

    public List<Vin> getClientVines(String referenceClient) {
        List<Vin> vines = entityManager.createNamedQuery("Vine.getByClientRef", Vin.class).setParameter("referenceClient", referenceClient).getResultList();
        return vines;
    }
}
