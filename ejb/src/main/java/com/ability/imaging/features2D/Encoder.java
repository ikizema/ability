package com.ability.imaging.features2D;

import org.apache.commons.codec.binary.Base64;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by ikizema on 15-09-02.
 */
public class Encoder {
    private int height = 0;
    private int width = 0;
    private int channels = 0;
    private String encodedString = "";

    public Encoder() {
    }

    public Encoder(int height, int width, int channels, String encodedString) {
        this.height = height;
        this.encodedString = encodedString;
        this.channels = channels;
        this.width = width;
    }

    public void EncoderConstruct(String encodedString, int height, int width, int channels) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.encodedString = encodedString;
        this.height = height;
        this.width = width;
        this.channels = channels;
    }

    public void MatToString(Mat matrix) {
        this.height = matrix.height();
        this.width = matrix.width();
        this.channels = matrix.channels();
        MatOfByte bytes = new MatOfByte(matrix.reshape(1, this.height * this.width * this.channels));
        byte[] bytes_ = bytes.toArray();
        this.encodedString = DatatypeConverter.printBase64Binary(bytes_);
    }

    public Mat EncoderToMat() {
        byte[] bytes_ = DatatypeConverter.parseBase64Binary(this.getEncodedString());
        MatOfByte bytes = new MatOfByte(bytes_);
        Mat desc = new Mat(this.getHeight() * this.getWidth() * this.getChannels(), 1, CvType.CV_8UC3);
        bytes.convertTo(desc, CvType.CV_8UC3);
        desc = desc.reshape(this.getChannels(), this.getHeight());
        return desc;
    }

    public Mat StringToMat(String string, int matrixHeight, int matWidth, int matChannels) {
        this.height = matrixHeight;
        this.width = matWidth;
        this.channels = matChannels;
        byte[] bytes_ = Base64.decodeBase64(string);
        MatOfByte bytes = new MatOfByte(bytes_);
        Mat desc = new Mat(matrixHeight * matWidth * matChannels, 1, CvType.CV_8UC3);
        bytes.convertTo(desc, CvType.CV_8UC3);
        desc = desc.reshape(matWidth, matrixHeight);
        return desc;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getChannels() {
        return this.channels;
    }

    public String getEncodedString() {
        return this.encodedString;
    }
}

