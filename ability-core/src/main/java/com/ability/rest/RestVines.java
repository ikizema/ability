package com.ability.rest;

import com.ability.model.Vin;
import com.ability.service.ServiceVin;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by ikizema on 15-08-26.
 */
@Stateless
@Path("/vine")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestVines {
    private static final Logger logger = Logger.getLogger(RestVines.class.getCanonicalName());

    @EJB
    ServiceVin serviceVin;

    @Path("/all")
    @GET
    public Vin getAllVine()  {
        logger.info("Begin getAllVine");
        long start = System.currentTimeMillis();
        Vin vin = new Vin();
        List<Vin> vines = serviceVin.getClientVines("SAQ");
        logger.info("End getAllVine : "+ (System.currentTimeMillis()-start) + " ms.");
        return vines.get(0);
    }
}
