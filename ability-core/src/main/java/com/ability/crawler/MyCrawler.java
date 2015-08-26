package com.ability.crawler;

import com.ability.model.Vin;
import com.ability.parsing.Reader;
import com.ability.service.ServiceVin;
import edu.uci.ics.crawler4j.crawler.WebCrawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Created by ikizema on 15-08-21.
 */
@Stateless
@LocalBean
public class MyCrawler extends WebCrawler {
    private static final Logger logger = LoggerFactory.getLogger(MyCrawler.class.getCanonicalName());
    private String context = "SAQ";
    private List<String> pageFilters = Arrays.asList("http://www.saq.com/page/fr/saqcom/vin-rouge");

    @EJB
    ServiceVin serviceVin;

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp3|zip|gz))$");

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        boolean filter = true;
        for (String pageFilter : this.getPageFilters()) {
            filter = href.startsWith(pageFilter);
        }
        return !FILTERS.matcher(href).matches()
                && filter;
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        Vin vin = Reader.getVineFromUrl(this.getContext(), url);
        serviceVin.save(vin);
        logger.info(vin.toString());
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<String> getPageFilters() {
        return pageFilters;
    }

    public void setPageFilters(List<String> pageFilters) {
        this.pageFilters = pageFilters;
    }
}
