package com.ability.imaging.features2D;

import org.opencv.core.DMatch;
import org.opencv.core.MatOfDMatch;

import java.util.List;

/**
 * Created by ikizema on 15-09-02.
 */
public class MatchAnalysis {
    private MatOfDMatch matches = new MatOfDMatch();

    public MatchAnalysis(MatOfDMatch matches) {
        this.matches = matches;
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
}

