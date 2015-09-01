package com.ability.service;

import com.ability.model.Member;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class ServiceMemberRegistration {

    @Inject
    private Logger logger;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Member> memberEventSrc;

    public void register(Member member) throws Exception {
        logger.info("Registering " + member.getName());
        em.persist(member);
        memberEventSrc.fire(member);
    }
}
