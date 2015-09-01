package com.ability.imaging;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class FaceDetector {
    private static final Logger logger = LoggerFactory.getLogger(ImageDescriptor.class.getCanonicalName());
    private static ImageManipulator imageManipulator = new ImageManipulator();

    public static void main(String[] args) throws MalformedURLException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        URL imageURL = new URL("http://www.hack4fun.org/h4f/sites/default/files/bindump/lena_secret.bmp");

        CascadeClassifier faceDetector = new CascadeClassifier("/opt/lib/opencv-3.0.0/data/haarcascades/haarcascade_frontalface_default.xml");
        Mat image = imageManipulator.getMat(imageURL);

        String filename = "./crawl/" + "lena_secret.bmp";
        logger.info(String.format("Writing %s", filename));
        Imgcodecs.imwrite(filename, image);

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        logger.info(String.format("Detected %s faces", faceDetections.toArray().length));

        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }

        filename = "./crawl/face_" + "lena_secret.bmp";
        logger.info(String.format("Writing %s", filename));
        Imgcodecs.imwrite(filename, image);
    }
}
