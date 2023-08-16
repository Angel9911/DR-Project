package com.example.demo.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="package_status")
public class StatusPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long status_id;
    @Column(name="status_type",nullable = false)
    private String status_type;

    public StatusPackage(Long status_id, String status_type) {
        this.status_id = status_id;
        this.status_type = status_type;

    }

    public StatusPackage() {

    }

    public Long getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Long status_id) {
        this.status_id = status_id;
    }

    public String getStatus_type() {
        return status_type;
    }

    public void setStatus_type(String status_type) {
        this.status_type = status_type;
    }

}
