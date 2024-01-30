package com.example.demo.models.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="type_package")
public class TypePackage implements Comparable<TypePackage>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;
    @Column(name="type_name",nullable = false)
    private String type_name;


    public TypePackage(Long type_id, String type_name) {
        this.typeId = type_id;
        this.type_name = type_name;
    }

    public TypePackage() {

    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long type_id) {
        this.typeId = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    @Override
    public int compareTo(TypePackage o) {
        int compareIds = this.getTypeId().compareTo(o.getTypeId());

        return compareIds;
    }
}
