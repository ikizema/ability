package com.ability.rest;

import com.ability.dto.CrawlerRequest;
import com.ability.service.ServiceCrawler;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.logging.Logger;

/**
 * Created by ikizema on 15-08-26.
 */

@Path("/crawler")
@RequestScoped
public class RestCrawler {
    private static final Logger logger = Logger.getLogger(RestCrawler.class.getCanonicalName());

    @Path("/request")
    @POST
    public CrawlerRequest processCrawlerRequest(CrawlerRequest crawlerRequest) throws Exception {
        logger.info("Begin processCrawlerRequest");
        long start = System.currentTimeMillis();
        ServiceCrawler serviceCrawler = new ServiceCrawler(crawlerRequest);
        serviceCrawler.testSave();
        logger.info("End processCrawlerRequest : "+ (System.currentTimeMillis()-start) + " ms.");
        return crawlerRequest;
    }
}
