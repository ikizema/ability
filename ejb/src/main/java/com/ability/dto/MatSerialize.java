package com.ability.dto;

import org.opencv.core.Mat;

import java.io.Serializable;

/**
 * Created by ikizema on 15-09-14.
 */
public class MatSerialize extends Mat implements Serializable {
    public MatSerialize() {
        super();
    }
    public MatSerialize(Mat mat) {
        super();
    }
}
