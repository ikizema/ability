package com.ability.imaging.processing;

import com.ability.imaging.features2D.ImageFeatures2D;
import com.ability.imaging.features2D.MatchAnalysis;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.features2d.DescriptorMatcher;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikizema on 15-09-02.
 */
public class FindBestMatch {
    private List<Integer> listMoviesID = new ArrayList();
    private List<Float> listMoviesMatchDistance = new ArrayList();
    private ImageFeatures2D image = new ImageFeatures2D();

    public FindBestMatch() {
    }

    public static void main(String[] args) throws MalformedURLException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        FindBestMatch image = new FindBestMatch();
        InMemoryDescriptors filmDescriptors = new InMemoryDescriptors("ORB_ORB");
        URL imageURL = new URL("http://www.hack4fun.org/h4f/sites/default/files/bindump/lena_secret.bmp");
        image.loadImage(imageURL);

        try {
            image.generateMatches(filmDescriptors);
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

        System.out.println(image.getTopMatches(Integer.valueOf(5)));
    }

    public void loadImage(URL url) {
        this.image.setImage(url);
    }

    public void loadImage(File file) {
        this.image.setImage(file);
    }

    public void generateMatches(InMemoryDescriptors filmDescriptors) throws SQLException {
        for(int i = 0; i < filmDescriptors.getSize(); ++i) {
            this.listMoviesID.add((Integer)filmDescriptors.getListMovieID().get(i));
            MatOfDMatch matches = new MatOfDMatch();
            DescriptorMatcher matcher = DescriptorMatcher.create(2);
            matcher.match(this.image.getImageDescriptor(), (Mat)filmDescriptors.getListMovieDescriptorMAT().get(i), matches);
            MatchAnalysis matchesAnalyse = new MatchAnalysis(matches);
            this.listMoviesMatchDistance.add(matchesAnalyse.getMin());
        }

    }

    public List<Integer> getTopMatches(Integer nbMatches) {
        ArrayList bestMatchMoviesID = new ArrayList();

        for(int i = 0; i < nbMatches.intValue() - 1; ++i) {
            Float tmpMin = Float.valueOf(2000.0F);
            Integer tmpPosition = null;

            for(int j = 0; j < this.listMoviesMatchDistance.size(); ++j) {
                if(tmpMin.floatValue() > ((Float)this.listMoviesMatchDistance.get(j)).floatValue()) {
                    tmpMin = (Float)this.listMoviesMatchDistance.get(j);
                    tmpPosition = Integer.valueOf(j);
                }
            }

            bestMatchMoviesID.add((Integer)this.listMoviesID.get(tmpPosition.intValue()));
            this.listMoviesMatchDistance.set(tmpPosition.intValue(), Float.valueOf(2000.0F));
        }

        return bestMatchMoviesID;
    }
}

