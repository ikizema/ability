package com.ability.model;

import org.hibernate.validator.constraints.NotBlank;
import scala.Int;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by ikizema on 15-08-21.
 */

@Entity
@Table(name = "VINS", schema = "public", catalog = "ability")
@NamedQueries({
        @NamedQuery(name = "Vine.getByClientRef",
                query = "SELECT DISTINCT v FROM Vin v WHERE v.referenceClient = :referenceClient ORDER by v.codeSAQ"),
})
public class Vin extends BaseModel {

    @NotBlank
    @Column(name = "client_ref", nullable = false, length = 2000)
    private String referenceClient;

    @NotBlank
    @Column(name = "PRODUCT_URL", nullable = false, unique=true, length = 2000)
    private String referenceURL;

    @NotBlank
    @Column(name = "IMAGE_URL", nullable = false, length = 2000)
    private String imageURL;

    @NotBlank
    @Column(name = "product_name", nullable = false, length = 2000)
    private String productName;

    @NotBlank
    @Column(name = "product_type", nullable = false)
    private String productType;

    @NotBlank
    @Column(name = "code_SAQ", nullable = false, unique=true)
    private String codeSAQ;

    @NotBlank
    @Column(name = "code_CPU", nullable = false, unique=true)
    private String codeCPU;

    @Column(name = "produceCountry", nullable = true)
    private String produceCountry;

    @Column(name = "produceRegion", nullable = true)
    private String produceRegion;

    @Column(name = "producer", nullable = true)
    private String producer;

    @Column(name = "deg_alc", nullable = true)
    private String degAlcool;

    public Vin() {
    }

    public Vin(String referenceClient, String referenceURL) {
        this.referenceClient = referenceClient;
        this.referenceURL = referenceURL;
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
        return "Vin{" +
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
