package com.ability.service;

import com.ability.data.DescriptorRepository;
import com.ability.model.Descriptor;
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
 * Created by ikizema on 15-09-14.
 */
@Stateless
public class ServiceDescriptor {
    @Inject
    private Logger logger;

    @Inject
    private EntityManager em;

    @Inject
    private DescriptorRepository descriptorRepository;

    @Inject
    private Validator validator;

    @Inject
    private Event<Descriptor> descriptorEventSrc;

    public void save(Descriptor descriptor) {
        this.validateDescriptor(descriptor);
        this.persist(descriptor);
        logger.info("Descriptor Saved : "+descriptor.getVine().toString());
    }

    private void persist(Descriptor descriptor) {
        em.persist(descriptor);
        descriptorEventSrc.fire(descriptor);
    }

    private void validateDescriptor(Descriptor descriptor) {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Descriptor>> violations = validator.validate(descriptor);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        // Check the uniqueness of the Reference URL address
//        if (descForVineAlreadyExists(descriptor.getVine())) {
//            throw new ValidationException("Descriptor for vine exist.");
//        }
    }

    private boolean descForVineAlreadyExists(Vine vine) {
        Descriptor descriptor = null;
        try {
            descriptor = descriptorRepository.findAllByVine(vine);
        } catch (NoResultException e) {
            // ignore
        }
        return vine != null;
    }
}
