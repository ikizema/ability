package com.ability.imaging.features2D;

import org.opencv.core.*;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ikizema on 15-09-02.
 */
public class ImageFeatures2D {
    private static final Logger logger = LoggerFactory.getLogger(ImageFeatures2D.class.getCanonicalName());
    private static ImageManipulator imageManipulator = new ImageManipulator();
    Encoder encoder = new Encoder();
    private BufferedImage newImage = null;
    private Mat imageMat = null;
    private Mat imageMatGrey = null;
    private Mat imageDescriptor = null;
    private Mat imageDrawMat = null;
    private MatOfKeyPoint imageKeypoints = null;
    private FeatureDetector featureDetector = null;
    private DescriptorExtractor descriptorExtractor = null;
    private int detectorType = FeatureDetector.DYNAMIC_ORB;;        // default ORB
    private int descriptorType = DescriptorExtractor.BRISK;         // default BRISK

    public static void main(String[] args) throws MalformedURLException {
        try {
            Loader.loadLibrary("opencv_java300");
        } catch (java.lang.NoClassDefFoundError ex) {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
//        URL imageURL = new URL("http://s7d9.scene7.com/is/image/SAQ/00518712-1?rect=0,0,1000,1500&scl=1&id=-Hgqe3");
        URL imageURL = new URL("http://www.hack4fun.org/h4f/sites/default/files/bindump/lena_secret.bmp");
        ImageFeatures2D newFeature2D = new ImageFeatures2D(imageURL, true, FeatureDetector.DYNAMIC_ORB, DescriptorExtractor.BRIEF);
        imageManipulator.displayImage(newFeature2D.getImage());
        imageManipulator.displayImage(newFeature2D.getDrawMat());
        logger.info(newFeature2D.getImageDescriptorEncoded());
    }

    public int getDetectorType() {
        return detectorType;
    }

    public void setDetectorType(int detectorType) {
        this.detectorType = detectorType;
    }

    public int getDescriptorType() {
        return descriptorType;
    }

    public void setDescriptorType(int descriptorType) {
        this.descriptorType = descriptorType;
    }

    public ImageFeatures2D() {
        try {
            Loader.loadLibrary("opencv_java300");
        } catch (java.lang.NoClassDefFoundError ex) {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
    }

    public ImageFeatures2D(URL url, boolean encoded) {
        try {
            Loader.loadLibrary("opencv_java300");
        } catch (java.lang.NoClassDefFoundError ex) {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
        this.setImage(url);
        this.generateMatrices(encoded);
    }

    public ImageFeatures2D(BufferedImage bufferedImage, boolean encoded) {
        try {
            Loader.loadLibrary("opencv_java300");
        } catch (java.lang.NoClassDefFoundError ex) {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
        this.setImage(bufferedImage);
        this.generateMatrices(encoded);
    }

    public ImageFeatures2D(URL url, boolean encoded, int detectorType, int descriptorType) {
        try {
            Loader.loadLibrary("opencv_java300");
        } catch (java.lang.NoClassDefFoundError ex) {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
        this.setImage(url);
        this.generateMatrices(encoded);
    }

    public void generateMatrices(Boolean encode) {
        byte[] data = ((DataBufferByte)this.newImage.getRaster().getDataBuffer()).getData();
        this.imageMat = new Mat(this.newImage.getHeight(), this.newImage.getWidth(), CvType.CV_8UC3);
        this.imageMat.put(0, 0, data);
        this.imageMatGrey = new Mat(this.newImage.getHeight(), this.newImage.getWidth(), CvType.CV_8UC1);
        Imgproc.cvtColor(this.imageMat, this.imageMatGrey, 7);
        this.featureDetector = FeatureDetector.create(this.detectorType);
        this.imageKeypoints = new MatOfKeyPoint();
        this.featureDetector.detect(this.imageMat, this.imageKeypoints);
        this.descriptorExtractor = DescriptorExtractor.create(this.descriptorType);
        this.imageDescriptor = new Mat();
        this.descriptorExtractor.compute(this.imageMat, this.imageKeypoints, this.imageDescriptor);
        this.imageDrawMat = new Mat();
        Features2d.drawKeypoints(this.imageMat, this.imageKeypoints, this.imageDrawMat);
        // Encodage de l'information
        if (encode) {
            this.encoder.MatToString(this.imageDescriptor);
        }
    }

    public void setImage(URL url) {
        try {
            this.newImage = ImageIO.read(url);
        } catch (IOException var3) {
            ;
        }

    }

    public void setImage(Image newImage) {
        this.newImage = (BufferedImage)newImage;
    }

    public void setImage(BufferedImage newImage) {
        this.newImage = newImage;
    }

    public void setImage(File file) {
        try {
            this.newImage = ImageIO.read(file);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public BufferedImage getImage() {
        return this.newImage;
    }

    public MatOfKeyPoint getImageKeypoints() {
        return this.imageKeypoints;
    }

    public Mat getDrawMat() {
        return this.imageDrawMat;
    }

    public Mat getImageMat() {
        return this.imageMat;
    }

    public Mat getImageMatGrey() {
        return this.imageMatGrey;
    }

    public Mat getImageDescriptor() {
        return this.imageDescriptor;
    }

    public String getImageDescriptorEncoded() {
        return this.encoder.getEncodedString();
    }

    public Encoder getEncoder() {
        return encoder;
    }
}

