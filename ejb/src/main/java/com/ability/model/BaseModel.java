package com.ability.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Created by ikizema on 15-08-21.
 */
@MappedSuperclass
public abstract class BaseModel implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ABILITY")
//    @SequenceGenerator(name = "SEQ_ABILITY", sequenceName = "SEQ_ABILITY", allocationSize = 20)
//    @GeneratedValue(strategy=GenerationType.AUTO)
    @GeneratedValue
    protected Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    protected Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATE")
    protected Date updateDate;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (this.createDate == null) {
            this.createDate = new Date();
        }
        this.updateDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseModel)) return false;

        BaseModel baseModel = (BaseModel) o;

        if (id != null ? !id.equals(baseModel.id) : baseModel.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public BaseModel() {
    }
}