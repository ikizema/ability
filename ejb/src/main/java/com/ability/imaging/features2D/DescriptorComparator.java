package com.ability.imaging.features2D;

import com.ability.parquet.model.VineParquet;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.features2d.DescriptorMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ability.imaging.Copmarator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikizema on 15-09-11.
 */
public class DescriptorComparator {
    private static final Logger logger = LoggerFactory.getLogger(DescriptorComparator.class.getCanonicalName());
    private Copmarator comparator;

    public DescriptorComparator(String filename) {
        this.comparator = new Copmarator(filename);
    }

    public VineParquet findBestMatch(File file) {
        try {
            return findBestMatch(ImageIO.read(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public VineParquet findBestMatch(BufferedImage image) {
        ImageFeatures2D imageFeatures2D = new ImageFeatures2D(image, false);
        float minDistance = 10000;
        VineParquet bestMatch = new VineParquet();
        for (VineParquet storedVine : (List<VineParquet>) this.comparator.getData()) {
            MatOfDMatch matches = new MatOfDMatch();
            DescriptorMatcher matcher = DescriptorMatcher.create(2);
            Encoder decoder = new Encoder(storedVine.getDescriptor().getDescriptorHeight(), storedVine.getDescriptor().getDescriptorWidth(), storedVine.getDescriptor().getDescriptorChannels(), storedVine.getDescriptor().getDescriptorData().toString());
            Mat matrixDescriptor = decoder.EncoderToMat();
            matcher.match(imageFeatures2D.getImageDescriptor(), matrixDescriptor, matches);
            MatchAnalysis matchesAnalyse = new MatchAnalysis(matches);
            if (matchesAnalyse.getMin() < minDistance) {
                minDistance=matchesAnalyse.getMin();
                bestMatch=storedVine;
            }
        }
        return bestMatch;
    }
}
