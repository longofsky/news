package com.patent.news.entity;


import com.patent.news.util.UniqueString;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "deleted", columnDefinition = "Bit(1) default false")
    private boolean deleted = false;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_time")
    private Date updatedTime;

    @PrePersist
    protected void prePersist() {
        if (StringUtils.isBlank(this.id)) id = UniqueString.uuidUniqueString();
        if (this.createdTime == null) createdTime = new Date();
        if (this.updatedTime == null) updatedTime = new Date();
        if (this.createdBy == null) createdBy = "1";
        if (this.updatedBy == null) updatedBy = "1";
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedTime = new Date();
    }

    @PreRemove
    protected void preRemove() {
        this.updatedTime = new Date();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
