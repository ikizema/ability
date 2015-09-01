package com.ability.rest;

import com.ability.data.VineRepository;
import com.ability.dto.CrawlerRequest;
import com.ability.model.Vine;
import com.ability.service.ServiceCrawler;
import com.ability.service.ServiceVin;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by ikizema on 15-08-26.
 */

@Path("/crawler")
@RequestScoped
public class RestCrawler {
    @Inject
    private Logger logger;

    @Inject
    ServiceCrawler serviceCrawler;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CrawlerRequest listAllMembers() {
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CrawlerRequest processCrawlerRequest(CrawlerRequest crawlerRequest) throws Exception {
        logger.info("Begin processCrawlerRequest");
        long start = System.currentTimeMillis();
        serviceCrawler.run(crawlerRequest);
        logger.info("End processCrawlerRequest : "+ (System.currentTimeMillis()-start) + " ms.");
        return crawlerRequest;
    }
}
