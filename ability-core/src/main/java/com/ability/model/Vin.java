package com.ability.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by ikizema on 15-08-21.
 */

@Entity
@Table(name = "VIN", schema = "public", catalog = "ability")
public class Vin extends BaseModel {

    @NotBlank
    @Column(name = "PRODUCT_URL", nullable = false)
    private String referenceURL;

    @NotBlank
    @Column(name = "client_ref", nullable = false)
    private String referenceClient;

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
}
