package com.ability.service;

import com.ability.data.VineRepository;
import com.ability.model.Vine;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by ikizema on 15-08-26.
 */
@Stateless
public class ServiceVin {

    @Inject
    private Logger logger;

    @Inject
    private EntityManager em;

    @Inject
    private VineRepository repository;

    @Inject
    private Validator validator;

    @Inject
    private Event<Vine> vinEventSrc;

    public void save(Vine vine) throws Exception {
        this.validateVine(vine);
        this.persist(vine);
    }

    private void persist(Vine vine) throws Exception {
        em.persist(vine);
        vinEventSrc.fire(vine);
    }

    private void validateVine(Vine vine) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Vine>> violations = validator.validate(vine);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        // Check the uniqueness of the Reference URL address
        if (referenceAlreadyExists(vine.getReferenceURL())) {
            throw new ValidationException("Unique URL Reference Violation");
        }
        // Check the uniqueness of the Code CPU
        if (codeCpuAlreadyExists(vine.getCodeCPU())) {
            throw new ValidationException("Unique Code CPU Violation");
        }

        // Check the uniqueness of the Code SAQ
        if (codeSaqAlreadyExists(vine.getCodeSAQ())) {
            throw new ValidationException("Unique Code SAQ Violation");
        }
    }

    private boolean referenceAlreadyExists(String referenceURL) {
        Vine vine = null;
        try {
            vine = repository.findByReferenceURL(referenceURL);
        } catch (NoResultException e) {
            // ignore
        }
        return vine != null;
    }

    private boolean codeCpuAlreadyExists(String referenceURL) {
        Vine vine = null;
        try {
            vine = repository.findByReferenceURL(referenceURL);
        } catch (NoResultException e) {
            // ignore
        }
        return vine != null;
    }

    private boolean codeSaqAlreadyExists(String referenceURL) {
        Vine vine = null;
        try {
            vine = repository.findByReferenceURL(referenceURL);
        } catch (NoResultException e) {
            // ignore
        }
        return vine != null;
    }
}
