package com.example.demo.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="package_status")
public class StatusPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;
    @Column(name="status_type",nullable = false)
    private String status_type;

    public StatusPackage(Long statusId, String status_type) {
        this.statusId = statusId;
        this.status_type = status_type;

    }

    public StatusPackage() {

    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatus_type() {
        return status_type;
    }

    public void setStatus_type(String status_type) {
        this.status_type = status_type;
    }

}
