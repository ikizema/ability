package com.ability.service;

import com.ability.model.Test;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ikizema on 15-08-26.
 */
@Stateless(name = "ServiceTest", mappedName = "ServiceTest")
//@LocalBean
public class ServiceTest {
    private static final Logger logger = Logger.getLogger(ServiceTest.class.getCanonicalName());

    @PersistenceContext(unitName = "ability")
    EntityManager entityManager;

    public ServiceTest() {
    }

    public List<Test> getAllTest() {
        Test test = new Test();
        List<Test> tests = new ArrayList<Test>();
        tests.add(test);
//        List<Test> tests = entityManager.createNamedQuery("Test.findAll", Test.class)
//                .getResultList();
        return tests;
    }
}
