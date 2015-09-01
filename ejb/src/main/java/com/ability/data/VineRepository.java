package com.ability.data;

import com.ability.model.Vine;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class VineRepository {

    @Inject
    private EntityManager em;

    public Vine findById(Long id) {
        return em.find(Vine.class, id);
    }

    public Vine findByReferenceURL(String referenceURL) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vine> criteria = cb.createQuery(Vine.class);
        Root<Vine> vine = criteria.from(Vine.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(Member_.email), email));
        criteria.select(vine).where(cb.equal(vine.get("referenceURL"), referenceURL));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Vine> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vine> criteria = cb.createQuery(Vine.class);
        Root<Vine> vine = criteria.from(Vine.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(vine).orderBy(cb.asc(vine.get("productName")));
        return em.createQuery(criteria).getResultList();
    }
}
