package com.ability.rest;

import javax.enterprise.context.RequestScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.logging.Logger;
import com.ability.model.Vine;
import com.ability.parquet.model.VineParquet;
import com.ability.service.ServiceImageComparator;

/**
 * Created by ikizema on 15-09-11.
 */
@Path("/images")
@RequestScoped
public class RestImage {
    @Inject
    private Logger logger;

    @Inject
    private ServiceImageComparator comparator;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("image/jpeg")
    public VineParquet findVine(BufferedInputStream stream) {
//        try {
//            return comparator.SaqVineComparator.getBestMatch(ImageIO.read(stream));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
