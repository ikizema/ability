package com.ability.model;

import com.ability.imaging.features2D.ImageFeatures2D;
import org.hibernate.validator.constraints.NotEmpty;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ikizema on 15-08-21.
 */

@Entity
@XmlRootElement
@Table(name = "Vine")
public class Vine extends BaseModel {

    @NotNull
    @NotEmpty
    @Column(name = "client_ref", length = 2000)
    private String referenceClient;

    @NotNull
    @NotEmpty
    @Column(name = "PRODUCT_URL", unique=true, length = 2000)
    private String referenceURL;

    @NotNull
    @NotEmpty
    @Column(name = "IMAGE_URL", length = 2000)
    private String imageURL;

    @NotNull
    @NotEmpty
    @Column(name = "product_name", length = 2000)
    private String productName;

    @NotNull
    @NotEmpty
    @Column(name = "product_type")
    private String productType;

    @NotNull
    @NotEmpty
    @Column(name = "code_SAQ", unique=true)
    private String codeSAQ;

    @NotNull
    @NotEmpty
    @Column(name = "code_CPU", unique=true)
    private String codeCPU;

    @Column(name = "produceCountry")
    private String produceCountry;

    @Column(name = "produceRegion")
    private String produceRegion;

    @Column(name = "producer")
    private String producer;

    @Column(name = "deg_alc")
    private String degAlcool;

    public Vine() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceURL() {
        return referenceURL;
    }

    public void setReferenceURL(String referenceURL) {
        this.referenceURL = referenceURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getReferenceClient() {
        return referenceClient;
    }

    public void setReferenceClient(String referenceClient) {
        this.referenceClient = referenceClient;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getCodeSAQ() {
        return codeSAQ;
    }

    public void setCodeSAQ(String codeSAQ) {
        this.codeSAQ = codeSAQ;
    }

    public String getCodeCPU() {
        return codeCPU;
    }

    public void setCodeCPU(String codeCPU) {
        this.codeCPU = codeCPU;
    }

    public String getProduceRegion() {
        return produceRegion;
    }

    public void setProduceRegion(String produceRegion) {
        this.produceRegion = produceRegion;
    }

    public String getProduceCountry() {
        return produceCountry;
    }

    public void setProduceCountry(String produceCountry) {
        this.produceCountry = produceCountry;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getDegAlcool() {
        return degAlcool;
    }

    public void setDegAlcool(String degAlcool) {
        this.degAlcool = degAlcool;
    }

    @Override
    public String toString() {
        return "Vine{" +
                "referenceClient='" + referenceClient + '\'' +
                ", referenceURL='" + referenceURL + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", productName='" + productName + '\'' +
                ", productType='" + productType + '\'' +
                ", codeSAQ='" + codeSAQ + '\'' +
                ", codeCPU='" + codeCPU + '\'' +
                ", produceCountry='" + produceCountry + '\'' +
                ", produceRegion='" + produceRegion + '\'' +
                ", producer='" + producer + '\'' +
                ", degAlcool='" + degAlcool + '\'' +
                '}';
    }
}
