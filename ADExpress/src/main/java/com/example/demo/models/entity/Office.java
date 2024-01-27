package com.example.demo.models.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "offices")
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long office_id;
    @Column(name="office_location",nullable = false)
    private String office_location;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})// for relationship cities -> offices (many to one)
    @JoinColumn(name="city_id",referencedColumnName="city_id",insertable=true, updatable=true)
    private City city;

    public Office(Long office_id, String office_location, City city) {
        this.office_id = office_id;
        this.office_location = office_location;
        this.city = city;
    }

    public Office() {
    }

    public Long getOffice_id() {
        return office_id;
    }

    public void setOffice_id(Long office_id) {
        this.office_id = office_id;
    }

    public String getOffice_location() {
        return office_location;
    }

    public void setOffice_location(String office_location) {
        this.office_location = office_location;
    }

    public City getCity() {
        return city;
    }

    public void setCityId(City city) {
        this.city = city;
    }

    public void setCity(City city) {
        this.city = city;
    }


}
