package com.ability.service;

import com.ability.crawler.MyCrawler;
import com.ability.dto.CrawlerRequest;
import com.ability.model.Vin;
import com.ability.parsing.Reader;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ikizema on 15-08-26.
 */

@Stateless
@LocalBean
public class ServiceCrawler {
    private static final Logger logger = LoggerFactory.getLogger(ServiceCrawler.class.getCanonicalName());
    private String context;
    private List<String> beginURLs;
    private Integer maxPages;
    private List<String> pageFilters;

    @EJB
    ServiceVin serviceVin;

    public void main(String[] args) throws Exception {
        CrawlerRequest crawlerRequest = new CrawlerRequest();
        crawlerRequest.setContext("SAQ");
        crawlerRequest.setBeginURLs(Arrays.asList("http://www.saq.com/page/fr/saqcom/vin-rouge/14-hands-hot-to-trot-red-blend-2012/12245611"));
        crawlerRequest.setMaxPages(2);
        ServiceCrawler serviceCrawler = new ServiceCrawler(crawlerRequest);
        serviceCrawler.run();
    }

    public ServiceCrawler() {
    }

    public ServiceCrawler(CrawlerRequest crawlerRequest) {
        this.setBeginURLs(crawlerRequest.getBeginURLs());
        this.setContext(crawlerRequest.getContext());
        this.setMaxPages(crawlerRequest.getMaxPages());
        this.setPageFilters(crawlerRequest.getPageFilters());
    }

    public void testSave() {
        Vin vin = Reader.getVineFromUrl(this.getContext(), this.getBeginURLs().get(0));
        vin.setId((long) 2);
        logger.info(vin.toString());
        serviceVin.save(vin);
    }

    public void run() throws Exception {
        logger.debug("Begin ServiceCrawler runner...");

        /*
         * General Settings
         */
        String crawlStorageFolder = "./crawl";
        int numberOfCrawlers = 1;

        /*
         * CrawlConfigurations
         */
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        if (this.getMaxPages() != null) {
            config.setMaxPagesToFetch(this.getMaxPages());
        }

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        for (String url : this.getBeginURLs()) {
            controller.addSeed(url);
        }

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(MyCrawler.class, numberOfCrawlers);

        logger.debug("End ServiceCrawler runner !");
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getMaxPages() {
        return maxPages;
    }

    public void setMaxPages(Integer maxPages) {
        this.maxPages = maxPages;
    }

    public List<String> getBeginURLs() {
        return beginURLs;
    }

    public void setBeginURLs(List<String> beginURLs) {
        this.beginURLs = beginURLs;
    }

    public List<String> getPageFilters() {
        return pageFilters;
    }

    public void setPageFilters(List<String> pageFilters) {
        this.pageFilters = pageFilters;
    }
}
