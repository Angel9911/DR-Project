package com.example.demo.models;

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
    @ManyToOne(fetch = FetchType.LAZY)// for relationship cities -> offices (many to one)
    @JoinColumn(name="city_id",referencedColumnName="city_id",insertable=false, updatable=false)
    private City city;
    @OneToMany(mappedBy = "office", fetch = FetchType.LAZY,cascade = CascadeType.ALL) //for relationship office -> packages (one to many)
   // @JoinColumn(name="office_id",referencedColumnName="office_id",insertable=false, updatable=false)
    private List<Packages> packagesList;
    @OneToMany(mappedBy = "office_administrator", fetch = FetchType.LAZY,cascade = CascadeType.ALL) //for relationship office -> packages (one to many)
    // @JoinColumn(name="office_id",referencedColumnName="office_id",insertable=false, updatable=false)
    private List<Administrator> administrators;

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
