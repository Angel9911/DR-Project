package com.example.demo.models.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="type_package")
public class TypePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long type_id;
    @Column(name="type_name",nullable = false)
    private String type_name;
    @OneToMany(mappedBy = "typePackage", fetch = FetchType.LAZY,cascade = CascadeType.ALL) // for relationship cities -> offices (one to many)
   // @JoinColumn(name="type_id",referencedColumnName="type_package_id",insertable=false, updatable=false)
    private List<Packages> packagesList;

    public TypePackage(Long type_id, String type_name) {
        this.type_id = type_id;
        this.type_name = type_name;
    }

    public TypePackage() {

    }

    public Long getType_id() {
        return type_id;
    }

    public void setType_id(Long type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}
