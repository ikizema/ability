package com.ability.model;

import com.ability.imaging.features2D.Encoder;
import com.ability.imaging.features2D.ImageFeatures2D;
import org.hibernate.validator.constraints.NotEmpty;
import org.opencv.core.Mat;
import scala.Int;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ikizema on 15-09-14.
 */
@Entity
@XmlRootElement
@Table(name = "Descriptors")
public class Descriptor extends BaseModel {


    @OneToOne(targetEntity = Vine.class)
    @JoinColumn(name = "VINE_ID", nullable = false, unique=true,referencedColumnName = "ID", updatable = false)
    private Vine vine;

    @NotNull
    @NotEmpty
    @Column(name = "desc_type")
    private String descriptorType;

    @NotNull
    @NotEmpty
    @Column(name = "desc_height")
    private String descriptorHeight;

    @NotNull
    @NotEmpty
    @Column(name = "desc_width")
    private String descriptorWidth;

    @NotNull
    @NotEmpty
    @Column(name = "desc_channels")
    private String descriptorChannels;

    @NotNull
    @NotEmpty
    @Column(name = "desc_data", length = 80000)
    private String descriptorData;

    public Descriptor() {
    }

    public void setDescriptor(ImageFeatures2D image) {
        this.setDescriptorChannels(Integer.toString(image.getEncoder().getChannels()));
        this.setDescriptorData(image.getEncoder().getEncodedString());
        this.setDescriptorHeight(Integer.toString(image.getEncoder().getHeight()));
        this.setDescriptorWidth(Integer.toString(image.getEncoder().getWidth()));
        this.setDescriptorType(image.getDescriptorType()+"_"+image.getDetectorType());

    }

    public String getDescriptorType() {
        return descriptorType;
    }

    public void setDescriptorType(String descriptorType) {
        this.descriptorType = descriptorType;
    }

    public String getDescriptorHeight() {
        return descriptorHeight;
    }

    public void setDescriptorHeight(String descriptorHeight) {
        this.descriptorHeight = descriptorHeight;
    }

    public String getDescriptorWidth() {
        return descriptorWidth;
    }

    public void setDescriptorWidth(String descriptorWidth) {
        this.descriptorWidth = descriptorWidth;
    }

    public String getDescriptorChannels() {
        return descriptorChannels;
    }

    public void setDescriptorChannels(String descriptorChannels) {
        this.descriptorChannels = descriptorChannels;
    }

    public String getDescriptorData() {
        return descriptorData;
    }

    public void setDescriptorData(String descriptorData) {
        this.descriptorData = descriptorData;
    }

    public Descriptor(Vine vine) {
        this.vine = vine;
        try {
            processVine();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public Vine getVine() {
        return vine;
    }

    public void setVine(Vine vine) {
        this.vine = vine;
    }

    public void processVine() throws MalformedURLException {
        ImageFeatures2D image = new ImageFeatures2D();
        URL imageURL = new URL(this.vine.getImageURL());
        image.setImage(imageURL);
        image.generateMatrices(true);
        this.setDescriptor(image);
    }

    @Override
    public String toString() {
        return "Descriptor{" +
                "vine=" + vine +
                ", descriptorType='" + descriptorType + '\'' +
                ", descriptorHeight='" + descriptorHeight + '\'' +
                ", descriptorWidth='" + descriptorWidth + '\'' +
                ", descriptorChannels='" + descriptorChannels + '\'' +
                ", descriptorData='" + descriptorData + '\'' +
                '}';
    }
}
