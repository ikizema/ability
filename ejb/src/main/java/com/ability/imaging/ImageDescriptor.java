package com.ability.imaging;

import org.opencv.core.*;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Created by ikizema on 15-09-01.
 */
public class ImageDescriptor {
    private static final Logger logger = LoggerFactory.getLogger(ImageDescriptor.class.getCanonicalName());
    private static ImageManipulator imageManipulator = new ImageManipulator();
//    final static FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
//    final static DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.BRIEF);
//    final static DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

    public static void main(String[] args) throws Exception {
//        logger.info(System.getProperty("java.library.path"));
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        URL imageURL = new URL("http://s7d9.scene7.com/is/image/SAQ/00518712-1?rect=0,0,1000,1500&scl=1&id=-Hgqe3");
        URL imageURL = new URL("http://www.hack4fun.org/h4f/sites/default/files/bindump/lena_secret.bmp");
        processImage(imageURL);
    }

    public static void processImage(URL imageURL) {
//        BufferedImage image = imageManipulator.getBufferedImage(imageURL);
        Mat imageMat = imageManipulator.getMat(imageURL);
        if (imageMat == null || imageMat.height() ==0 || imageMat.width() ==0 ) {
            logger.info("Failed to load image at " + imageURL.getPath());
            return;
        }
//        imageManipulator.displayImage(image);
//        imageManipulator.displayImage(imageMat);

        FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
        MatOfKeyPoint matOfKeyPoints = new MatOfKeyPoint();
        Mat descriptors = new Mat();
        detector.detect(imageMat, matOfKeyPoints);
//        DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.BRIEF);
//        extractor.compute(imageMat, matOfKeyPoints, descriptors);

//        analyze(imageMat,matOfKeyPoints,descriptors);

//        if (!detector.empty()){
//            logger.info("Detector is not empty");
//            // Draw kewpoints
//            Mat outputImage = new Mat();
//            Scalar color = new Scalar(0, 0, 255); // BGR
//            int flags = Features2d.DRAW_RICH_KEYPOINTS; // For each keypoint, the circle around keypoint with keypoint size and orientation will be drawn.
//            Features2d.drawKeypoints(imageMat, matOfKeyPoints, outputImage, color, flags);
//            imageManipulator.displayImage(outputImage);
//        } else {
//            logger.info("Detector is empty");
//        }

    }

//    static void analyze(Mat image, MatOfKeyPoint keypoints, Mat descriptors){
//        detector.detect(image, keypoints);
//        extractor.compute(image, keypoints, descriptors);
//    }
//
//    static MatOfDMatch match(Mat desc1, Mat desc2){
//        MatOfDMatch matches = new MatOfDMatch();
//        matcher.match(desc1, desc2, matches);
//        return matches;
//    }




}