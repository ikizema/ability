package com.ability.service;

import com.ability.model.Vin;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.util.logging.Logger;

/**
 * Created by ikizema on 15-08-26.
 */
@Stateless
@LocalBean
public class ServiceVin {

    @Inject
    private Logger logger;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Vin> vinEventSrc;

    public void save(Vin vin) throws Exception {
        logger.info("Registering : " + vin.toString());
        em.persist(vin);
        vinEventSrc.fire(vin);
    }
}
