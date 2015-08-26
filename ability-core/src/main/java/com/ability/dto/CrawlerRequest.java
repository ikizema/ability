package com.ability.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ikizema on 15-08-26.
 */
public class CrawlerRequest implements Serializable {
    private static final long serialVersionUID = 5054735470435878925L;

    private String context;
    private List<String> beginURLs;
    private Integer maxPages;
    private List<String> pageFilters;

    public CrawlerRequest() {
    }

    public List<String> getPageFilters() {
        return pageFilters;
    }

    public void setPageFilters(List<String> pageFilters) {
        this.pageFilters = pageFilters;
    }

    public List<String> getBeginURLs() {
        return beginURLs;
    }

    public void setBeginURLs(List<String> beginURLs) {
        this.beginURLs = beginURLs;
    }

    public Integer getMaxPages() {
        return maxPages;
    }

    public void setMaxPages(Integer maxPages) {
        this.maxPages = maxPages;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
