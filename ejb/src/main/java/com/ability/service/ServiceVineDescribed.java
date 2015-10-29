package com.ability.service;

import com.ability.data.DescriptorRepository;
import com.ability.imaging.features2D.ImageFeatures2D;
import com.ability.imaging.features2D.MatchAnalysis;
import com.ability.model.Vine;
import com.ability.model.VineDescribed;
import org.opencv.core.MatOfDMatch;
import org.opencv.features2d.DescriptorMatcher;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by ikizema on 15-09-15.
 */
@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class ServiceVineDescribed {
    @Inject
    private Logger logger;

    @Inject
    private DescriptorRepository descriptorRepository;

    public enum States {BEFORESTARTED, STARTED, PAUSED, SHUTTINGDOWN};
    private States state;
    private List<VineDescribed> vineDescribeds;

    @PostConstruct
    public void initialize() {
        long start = System.currentTimeMillis();
        state = States.BEFORESTARTED;
        // Perform intialization
        this.setVineDescribeds(descriptorRepository.getAllVineDescribed());
        state = States.STARTED;
        logger.info("ServiceVineDescribed Started in : " + (System.currentTimeMillis() - start) + "ms. Loaded " + this.getSize() + " units.");
    }

    @PreDestroy
    public void terminate() {
        state = States.SHUTTINGDOWN;
        // Perform termination
        this.setVineDescribeds(null);
        logger.info("Shut down in progress");
    }

    public List<VineDescribed> getVineDescribeds() {
        return vineDescribeds;
    }

    public void setVineDescribeds(List<VineDescribed> vineDescribeds) {
        this.vineDescribeds = vineDescribeds;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    public void addVineDescribed(VineDescribed vineDescribed) {
        this.vineDescribeds.add(vineDescribed);
    }

    public int getSize() {
        return this.vineDescribeds.size();
    }

    public Vine findVineFromImage(BufferedImage image) {
        long start = System.currentTimeMillis();
        Vine vineBestMatch = new Vine();;
        ImageFeatures2D imageFeatures2D = new ImageFeatures2D(image, false);
        float minDistance = 10000;
        for (VineDescribed vineDescribed : (List<VineDescribed>) this.vineDescribeds) {
            MatOfDMatch matches = new MatOfDMatch();
            DescriptorMatcher matcher = DescriptorMatcher.create(2);
            matcher.match(imageFeatures2D.getImageDescriptor(), vineDescribed.getDescriptorMat(), matches);
            MatchAnalysis matchesAnalyse = new MatchAnalysis(matches);
            if (matchesAnalyse.getMinDistance() < minDistance) {
                minDistance=matchesAnalyse.getMinDistance();
                vineBestMatch=vineDescribed.getVine();
            }
            logger.info("Vine ref. URL : "+vineDescribed.getVine().getReferenceURL() + " " + matchesAnalyse.toString());
        }
        logger.info("Image research done in "+(System.currentTimeMillis()-start)+" ms.");
        logger.info("Image research distance is : "+minDistance);
        if ((int) minDistance < 200) {
            return vineBestMatch;
        } else {
            return null;
        }
    }

    public Vine findParallelVineFromImage(BufferedImage image) {
        final int nbThreads = 1;
        final ImageFeatures2D imageFeatures2D = new ImageFeatures2D(image, false);

        long start = System.currentTimeMillis();
        Vine vineBestMatch = new Vine();;
        float minDistance = 10000;

        List<ParallelThread> threads = new ArrayList<ParallelThread>();

        for(int threadNum=0; threadNum<nbThreads; threadNum++){
            ParallelThread parallelThread = new ParallelThread();
            parallelThread.setImageFeatures2D(imageFeatures2D);
            parallelThread.setVineDescribeds(this.getVineDescribeds().subList(threadNum * 100, vineDescribeds.size())); //(threadNum + 1) * 100 - 1));
            parallelThread.run();
            threads.add(parallelThread);
        }

        for(int thread=0; thread<threads.size(); thread++){
            MatOfDMatch matches = new MatOfDMatch();
            DescriptorMatcher matcher = DescriptorMatcher.create(2);
            matcher.match(imageFeatures2D.getImageDescriptor(), threads.get(thread).getVineDescribedBestMatch().getDescriptorMat(), matches);
            MatchAnalysis matchesAnalyse = new MatchAnalysis(matches);
            if (matchesAnalyse.getMinDistance() < minDistance) {
                minDistance=matchesAnalyse.getMinDistance();
                vineBestMatch=vineDescribeds.get(thread).getVine();
            }
        }

        logger.info("Image research done in "+(System.currentTimeMillis()-start)+" ms.");
        logger.info("Image research distance is : "+minDistance);
        if ((int) minDistance < 200) {
            return vineBestMatch;
        } else {
            return null;
        }
    }
}
