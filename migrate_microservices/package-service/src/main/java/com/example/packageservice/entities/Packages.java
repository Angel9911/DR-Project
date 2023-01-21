package com.example.packageservice.entities;

import jakarta.persistence.*;

//@Entity
//@Table(name="packages")
public class Packages {
  //  @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long package_id;
    //@Column(name="name_package",nullable = false)
    private String name_package;

    public Packages(Long package_id, String name_package) {
        this.package_id = package_id;
        this.name_package = name_package;
    }

    public Long getPackage_id() {
        return package_id;
    }

    public void setPackage_id(Long package_id) {
        this.package_id = package_id;
    }

    public String getName_package() {
        return name_package;
    }

    public void setName_package(String name_package) {
        this.name_package = name_package;
    }
}
