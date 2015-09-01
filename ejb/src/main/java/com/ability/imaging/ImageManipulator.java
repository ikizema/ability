package com.ability.imaging;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.net.URL;

/**
 * Created by ikizema on 15-09-01.
 */
public class ImageManipulator {
    private static final Logger logger = LoggerFactory.getLogger(ImageDescriptor.class.getCanonicalName());

    public ImageManipulator() {
    }

    public void displayImage(Mat matrix) {
        BufferedImage image = getBufferedImage(matrix);
        displayImage(image);
    }

    public void displayImage(URL imageURL) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayImage(image);
    }

    public void displayImage(BufferedImage image) {
        JFrame editorFrame = new JFrame("Image Demo");
        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);
        editorFrame.pack();
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setVisible(true);
    }

    /**
     * Converts/writes a Mat into a BufferedImage.
     *
     * @param matrix Mat of type CV_8UC3 or CV_8UC1
     * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY
     */
    public BufferedImage getBufferedImage(Mat matrix) {
        int cols = matrix.cols();
        int rows = matrix.rows();
        int elemSize = (int)matrix.elemSize();
        byte[] data = new byte[cols * rows * elemSize];
        int type;

        matrix.get(0, 0, data);

        switch (matrix.channels()) {
            case 1:
                type = BufferedImage.TYPE_BYTE_GRAY;
                break;

            case 3:
                type = BufferedImage.TYPE_3BYTE_BGR;

                // bgr to rgb
                byte b;
                for(int i=0; i<data.length; i=i+3) {
                    b = data[i];
                    data[i] = data[i+2];
                    data[i+2] = b;
                }
                break;

            default:
                return null;
        }

        BufferedImage image = new BufferedImage(cols, rows, type);
        image.getRaster().setDataElements(0, 0, cols, rows, data);

        return image;
    }

    public BufferedImage getBufferedImage(URL imageURL) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public Mat getMat(BufferedImage image){
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        int rows = image.getHeight();
        int cols = image.getWidth();
        int type = CvType.CV_8UC3;
        Mat newMat = new Mat(rows,cols,type);
        newMat.put(0, 0, pixels);
        return newMat;
    }

    public Mat getMat(URL url){
        Mat newMat = getMat(getBufferedImage(url));
        return newMat;
    }


}

