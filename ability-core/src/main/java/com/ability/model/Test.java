package com.ability.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ikizema on 15-08-26.
 */
@Entity
@Table(name = "tests", schema = "public", catalog = "ability")
@NamedQueries({
        @NamedQuery(name = "Test.findAll", query = "SELECT p FROM Test p"),
})
public class Test extends BaseModel {

    @Column(name = "test_ref")
    private String referenceClient;

    public Test() {
    }

    public Test(Long id, String referenceClient) {
        this.id = id;
        this.referenceClient = referenceClient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceClient() {
        return referenceClient;
    }

    public void setReferenceClient(String referenceClient) {
        this.referenceClient = referenceClient;
    }
}
