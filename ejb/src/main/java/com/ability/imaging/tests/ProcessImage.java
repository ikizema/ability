package com.ability.imaging.tests;

import com.ability.imaging.features2D.Encoder;
import org.opencv.core.Loader;
import org.opencv.core.Mat;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import com.ability.imaging.features2D.ImageFeatures2D;

/**
 * Created by ikizema on 15-09-02.
 */
public class ProcessImage {
    public static void main(String[] args) throws MalformedURLException {
        new ArrayList();
        Mat imageDescriptor = null;
        ImageFeatures2D image = new ImageFeatures2D();
        URL imageURL = new URL("http://s7d9.scene7.com/is/image/SAQ/00518712-1?rect=0,0,1000,1500&scl=1&id=-Hgqe3");
        image.setImage(imageURL);
        image.generateMatrices(false);
        imageDescriptor = image.getImageDescriptor();
        System.out.println(imageDescriptor.reshape(1, 1).dump());
        System.out.println(imageDescriptor.type());
        Encoder encoder = new Encoder();
        encoder.MatToString(imageDescriptor);
        String encodedMat = encoder.getEncodedString();
        System.out.println("encodedMat Length :"+encodedMat.length()+" "+encodedMat);
        Mat desc = encoder.EncoderToMat();
        System.out.println(imageDescriptor.reshape(1, 1).dump());
        System.out.println(imageDescriptor.type());
    }
}
