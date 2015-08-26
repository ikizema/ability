package com.ability.rest;

import com.ability.model.Test;
import com.ability.model.Vin;
import com.ability.service.ServiceTest;
import com.ability.service.ServiceVin;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by ikizema on 15-08-26.
 */
@Stateless
@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class RestTest {
    private static final Logger logger = Logger.getLogger(RestTest.class.getCanonicalName());

    @EJB(beanName="ServiceTest", mappedName="ServiceTest", beanInterface=ServiceTest.class)
    ServiceTest serviceTest;

    @Path("/all")
    @GET
    public List<Test> getAllTest()  {
        logger.info("Begin getAllTest");
        long start = System.currentTimeMillis();
//        List<Test> tests = serviceTest.getAllTest();
        Test test = new Test();
        List<Test> tests = new ArrayList<Test>();
        tests.add(test);
        logger.info("End getAllTest : "+ (System.currentTimeMillis()-start) + " ms.");
        return tests;
    }
}
