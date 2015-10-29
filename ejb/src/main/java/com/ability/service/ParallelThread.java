package com.ability.service;

import com.ability.imaging.features2D.ImageFeatures2D;
import com.ability.imaging.features2D.MatchAnalysis;
import com.ability.model.VineDescribed;
import org.opencv.core.MatOfDMatch;
import org.opencv.features2d.DescriptorMatcher;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ejb.Singleton;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by ikizema on 15-09-15.
 */

public class ParallelThread implements Runnable {
    @Inject
    private Logger logger;

    private List<VineDescribed> vineDescribeds;
    private ImageFeatures2D imageFeatures2D;
    private int id;

    private VineDescribed vineDescribedBestMatch = new VineDescribed();
    private float minDistance = 10000;

    public ParallelThread(int id) {
        this.id = id;
    }

    public ParallelThread(int id, ImageFeatures2D imageFeatures2D, List<VineDescribed> vineDescribeds) {
        this.id = id;
        this.imageFeatures2D = imageFeatures2D;
        this.vineDescribeds = vineDescribeds;
    }

    @Override
    public void run(){
        for (int i=0; i<this.getVineDescribeds().size(); i++) {
            MatOfDMatch matches = new MatOfDMatch();
            DescriptorMatcher matcher = DescriptorMatcher.create(2);
            matcher.match(imageFeatures2D.getImageDescriptor(), vineDescribeds.get(i).getDescriptorMat(), matches);
            MatchAnalysis matchesAnalyse = new MatchAnalysis(matches);
            if (matchesAnalyse.getMinDistance() < this.getMinDistance()) {
                this.setMinDistance(matchesAnalyse.getMinDistance());
                this.setVineDescribedBestMatch(vineDescribeds.get(i));
            }
            logger.info(Integer.toString(i));
        }
    }

    public ImageFeatures2D getImageFeatures2D() {
        return imageFeatures2D;
    }

    public void setImageFeatures2D(ImageFeatures2D imageFeatures2D) {
        this.imageFeatures2D = imageFeatures2D;
    }

    public List<VineDescribed> getVineDescribeds() {
        return vineDescribeds;
    }

    public void setVineDescribeds(List<VineDescribed> vineDescribeds) {
        this.vineDescribeds = vineDescribeds;
    }

    public ParallelThread() {
        super();
    }

    public float getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(float minDistance) {
        this.minDistance = minDistance;
    }

    public VineDescribed getVineDescribedBestMatch() {
        return vineDescribedBestMatch;
    }

    public void setVineDescribedBestMatch(VineDescribed vineDescribedBestMatch) {
        this.vineDescribedBestMatch = vineDescribedBestMatch;
    }
}
