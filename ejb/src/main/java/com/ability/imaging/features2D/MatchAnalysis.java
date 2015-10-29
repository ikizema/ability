package com.ability.imaging.features2D;

import org.opencv.core.DMatch;
import org.opencv.core.MatOfDMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikizema on 15-09-02.
 */
public class MatchAnalysis {
    private static final Logger logger = LoggerFactory.getLogger(MatchAnalysis.class.getCanonicalName());
    private MatOfDMatch matches = new MatOfDMatch();
    private List<DMatch> listofDMatch = new ArrayList<DMatch>();
    private int nbMatches = 0;
    private Float minDistance = Float.valueOf(2000.0F);
    private Float maxDistance = Float.valueOf(0.0F);
    private Float moyenneDistance = Float.valueOf(0.0F);

    public MatchAnalysis(MatOfDMatch matches) {
        this.matches = matches;
        this.listofDMatch=matches.toList();
        this.nbMatches=this.listofDMatch.size();
        this.processValues();
    }

    public MatOfDMatch getMatches() {
        return matches;
    }

    public void setMatches(MatOfDMatch matches) {
        this.matches = matches;
    }

    public List<DMatch> getListofDMatch() {
        return listofDMatch;
    }

    public void setListofDMatch(List<DMatch> listofDMatch) {
        this.listofDMatch = listofDMatch;
    }

    public int getNbMatches() {
        return nbMatches;
    }

    public void setNbMatches(int nbMatches) {
        this.nbMatches = nbMatches;
    }

    public Float getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(Float minDistance) {
        this.minDistance = minDistance;
    }

    public Float getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Float maxDistance) {
        this.maxDistance = maxDistance;
    }

    public Float getMoyenneDistance() {
        return moyenneDistance;
    }

    public void setMoyenneDistance(Float moyenneDistance) {
        this.moyenneDistance = moyenneDistance;
    }

    private void processValues(){
        for(int i = 0; i < this.getNbMatches(); i++) {
            if(listofDMatch.get(i).distance > maxDistance.floatValue()) {
                maxDistance = listofDMatch.get(i).distance;
            }

            if(listofDMatch.get(i).distance < minDistance.floatValue()) {
                minDistance = listofDMatch.get(i).distance;
            }

            moyenneDistance = moyenneDistance + listofDMatch.get(i).distance;
        }
        moyenneDistance=moyenneDistance/this.getNbMatches();
    }

    public Float getMin() {
        List matchesList = this.matches.toList();
        Float minDistance = Float.valueOf(2000.0F);
        Float maxDistance = Float.valueOf(0.0F);
        Float moyenneDistance = Float.valueOf(0.0F);

        for(Integer i = Integer.valueOf(0); (double)i.intValue() < this.matches.size().height; i = Integer.valueOf(i.intValue() + 1)) {
            if(((DMatch)matchesList.get(i.intValue())).distance > maxDistance.floatValue()) {
                maxDistance = Float.valueOf(((DMatch)matchesList.get(i.intValue())).distance);
            }

            if(((DMatch)matchesList.get(i.intValue())).distance < minDistance.floatValue()) {
                minDistance = Float.valueOf(((DMatch)matchesList.get(i.intValue())).distance);
            }

            moyenneDistance = Float.valueOf(moyenneDistance.floatValue() + ((DMatch)matchesList.get(i.intValue())).distance);
        }

        moyenneDistance = Float.valueOf((float)((double)moyenneDistance.floatValue() / this.matches.size().height));
        return minDistance;
    }

    public Float getMax() {
        List matchesList = this.matches.toList();
        Float minDistance = Float.valueOf(2000.0F);
        Float maxDistance = Float.valueOf(0.0F);
        Float moyenneDistance = Float.valueOf(0.0F);

        for(Integer i = Integer.valueOf(0); (double)i.intValue() < this.matches.size().height; i = Integer.valueOf(i.intValue() + 1)) {
            if(((DMatch)matchesList.get(i.intValue())).distance > maxDistance.floatValue()) {
                maxDistance = Float.valueOf(((DMatch)matchesList.get(i.intValue())).distance);
            }

            if(((DMatch)matchesList.get(i.intValue())).distance < minDistance.floatValue()) {
                minDistance = Float.valueOf(((DMatch)matchesList.get(i.intValue())).distance);
            }

            moyenneDistance = Float.valueOf(moyenneDistance.floatValue() + ((DMatch)matchesList.get(i.intValue())).distance);
        }

        moyenneDistance = Float.valueOf((float)((double)moyenneDistance.floatValue() / this.matches.size().height));
        return maxDistance;
    }

    public Float getAvrage() {
        List matchesList = this.matches.toList();
        Float minDistance = Float.valueOf(2000.0F);
        Float maxDistance = Float.valueOf(0.0F);
        Float moyenneDistance = Float.valueOf(0.0F);

        for(Integer i = Integer.valueOf(0); (double)i.intValue() < this.matches.size().height; i = Integer.valueOf(i.intValue() + 1)) {
            if(((DMatch)matchesList.get(i.intValue())).distance > maxDistance.floatValue()) {
                maxDistance = Float.valueOf(((DMatch)matchesList.get(i.intValue())).distance);
            }

            if(((DMatch)matchesList.get(i.intValue())).distance < minDistance.floatValue()) {
                minDistance = Float.valueOf(((DMatch)matchesList.get(i.intValue())).distance);
            }

            moyenneDistance = Float.valueOf(moyenneDistance.floatValue() + ((DMatch)matchesList.get(i.intValue())).distance);
        }

        moyenneDistance = Float.valueOf((float)((double)moyenneDistance.floatValue() / this.matches.size().height));
        return moyenneDistance;
    }

    @Override
    public String toString() {
        return "MatchAnalysis{" +
                "nbMatches=" + nbMatches +
                ", minDistance=" + minDistance +
                ", maxDistance=" + maxDistance +
                ", moyenneDistance=" + moyenneDistance +
                '}';
    }
}

