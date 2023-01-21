package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false)
    private Long city_id;
    @Column(name="city_name", nullable = false)
    private String city_name;
    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY,cascade = CascadeType.ALL) // for relationship cities -> offices (one to many)
    @JsonIgnore
    // @JoinColumn(name="city_id",referencedColumnName="city_id",insertable=false, updatable=false)
    private List<Office> officeList;

    public City(Long city_id, String city_name) {
        this.city_id = city_id;
        this.city_name = city_name;
    }

    public City() {

    }

    public Long getCity_id() {
        return city_id;
    }

    public void setCity_id(Long city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public List<Office> getOfficeList() {
        return officeList;
    }

    public void setOfficeList(List<Office> officeList) {
        this.officeList = officeList;
    }


}
