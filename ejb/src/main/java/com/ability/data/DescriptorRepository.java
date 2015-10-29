package com.ability.data;

import com.ability.model.Descriptor;
import com.ability.model.Vine;
import com.ability.model.VineDescribed;

import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by ikizema on 15-09-14.
 */
@Singleton
@ApplicationScoped
public class DescriptorRepository {
    @Inject
    private Logger logger;

    @Inject
    private EntityManager em;

    public Descriptor findById(Long id) {
        return em.find(Descriptor.class, id);
    }

    public Descriptor findAllByVine(Vine vine) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Descriptor> criteria = cb.createQuery(Descriptor.class);
        Root<Descriptor> desc = criteria.from(Descriptor.class);
        criteria.select(desc).where(cb.equal(desc.get("vine"), vine));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Descriptor> findAllOrderedByVineID() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Descriptor> criteria = cb.createQuery(Descriptor.class);
        Root<Descriptor> desc = criteria.from(Descriptor.class);
        criteria.select(desc).orderBy(cb.asc(desc.get("vine")));
        return em.createQuery(criteria).getResultList();
    }

    public List<VineDescribed> getAllVineDescribed() {
        List<Descriptor> descriptors = findAllOrderedByVineID();
        List<VineDescribed> vinesDescribed = new ArrayList<VineDescribed>();;
        if (descriptors == null) {
            return null;
        }
        for (int i=0; i< descriptors.size(); i++) {
            VineDescribed vineDescribed = new VineDescribed(descriptors.get(i));
            logger.info(vineDescribed.toString());
            vinesDescribed.add(vineDescribed);
        }
        return vinesDescribed;
    }
}
