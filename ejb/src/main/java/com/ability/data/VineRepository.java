package com.ability.data;

import com.ability.model.Descriptor;
import com.ability.model.Vine;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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

    public List<Vine> findAllOrderedById() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vine> criteria = cb.createQuery(Vine.class);
        Root<Vine> vine = criteria.from(Vine.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(vine).orderBy(cb.asc(vine.get("id")));
        return em.createQuery(criteria).getResultList();
    }

    public List<Vine> findAllNotDescribedOrderedById() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vine> query = cb.createQuery(Vine.class);
        Root<Vine> vine = query.from(Vine.class);
        query.select(vine);

        Subquery<Descriptor> subquery = query.subquery(Descriptor.class);
        Root<Descriptor> subRootEntity = subquery.from(Descriptor.class);
        subquery.select(subRootEntity);

        Predicate correlatePredicate = cb.equal(subRootEntity.get("vine"), vine);
        subquery.where(correlatePredicate);
        query.where(cb.not(cb.exists(subquery)));

        TypedQuery typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
