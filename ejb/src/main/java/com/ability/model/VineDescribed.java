package com.ability.model;

import com.ability.imaging.features2D.Encoder;
import org.opencv.core.Core;
import org.opencv.core.Loader;
import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ikizema on 15-09-15.
 */
public class VineDescribed {
    private static final Logger logger = LoggerFactory.getLogger(VineDescribed.class.getCanonicalName());
    private Vine vine;
    private Mat descriptorMat;
    private String descType;

    public VineDescribed() {
    }

    public VineDescribed(Descriptor descriptor) {
        try {
            Loader.loadLibrary("opencv_java300");
        } catch (java.lang.NoClassDefFoundError ex) {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
        Encoder decoder = new Encoder(descriptor);
        this.setDescriptorMat(decoder.EncoderToMat());
        this.setVine(descriptor.getVine());
        this.setDescType(descriptor.getDescriptorType());
    }

    public Mat getDescriptorMat() {
        return descriptorMat;
    }

    public void setDescriptorMat(Mat descriptorMat) {
        this.descriptorMat = descriptorMat;
    }

    public Vine getVine() {
        return vine;
    }

    public void setVine(Vine vine) {
        this.vine = vine;
    }

    public String getDescType() {
        return descType;
    }

    public void setDescType(String descType) {
        this.descType = descType;
    }

    @Override
    public String toString() {
        return "VineDescribed{" +
                "vine=" + vine +
                ", descriptorMat=" + descriptorMat +
                ", descType='" + descType + '\'' +
                '}';
    }
}
