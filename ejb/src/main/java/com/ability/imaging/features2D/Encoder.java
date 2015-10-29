package com.ability.imaging.features2D;

import com.ability.model.Descriptor;
import org.apache.commons.codec.binary.Base64;
import org.opencv.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by ikizema on 15-09-02.
 */
public class Encoder {
    private static final Logger logger = LoggerFactory.getLogger(Encoder.class.getCanonicalName());
    private int height = 0;
    private int width = 0;
    private int channels = 0;
    private String encodedString = "";

    public Encoder() {
    }

    public Encoder(Descriptor descriptor){
        this.setHeight(Integer.parseInt(descriptor.getDescriptorHeight().toString()));
        this.setWidth(Integer.parseInt(descriptor.getDescriptorWidth().toString()));
        this.setChannels(Integer.parseInt(descriptor.getDescriptorChannels().toString()));
        this.setEncodedString(descriptor.getDescriptorData().toString());
    }

    public Encoder(int height, int width, int channels, String encodedString) {
        this.height = height;
        this.encodedString = encodedString;
        this.channels = channels;
        this.width = width;
    }

    public void EncoderConstruct(String encodedString, int height, int width, int channels) {
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

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public void setEncodedString(String encodedString) {
        this.encodedString = encodedString;
    }

    @Override
    public String toString() {
        return "Encoder{" +
                "height=" + height +
                ", width=" + width +
                ", channels=" + channels +
                ", encodedString='" + encodedString + '\'' +
                '}';
    }
}

