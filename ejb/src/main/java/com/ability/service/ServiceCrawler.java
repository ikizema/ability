package com.ability.service;

import com.ability.crawler.MyCrawler;
import com.ability.dto.CrawlerRequest;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


/**
 * Created by ikizema on 15-08-26.
 */

@Stateless
public class ServiceCrawler {
    @Inject
    private Logger logger;

    public ServiceCrawler() {
    }

    public void main(String[] args) throws Exception {
        CrawlerRequest crawlerRequest = new CrawlerRequest();
        crawlerRequest.setContext("SAQ");
        crawlerRequest.setBeginURLs(Arrays.asList("http://www.saq.com/page/fr/saqcom/vin-rouge/14-hands-hot-to-trot-red-blend-2012/12245611"));
        crawlerRequest.setMaxPages(2);
        this.run(crawlerRequest);
    }

    public void run(CrawlerRequest crawlerRequest) throws Exception {
        logger.info("Begin ServiceCrawler runner...");

        MyCrawler mc = new MyCrawler();

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
        if (crawlerRequest.getMaxPages() != null) {
            config.setMaxPagesToFetch(crawlerRequest.getMaxPages());
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
        for (String url : crawlerRequest.getBeginURLs()) {
            controller.addSeed(url);
        }

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(MyCrawler.class, numberOfCrawlers);

        logger.info("End ServiceCrawler runner !");
    }

}
