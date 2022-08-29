package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="packages")
public class Packages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long package_id;
    @Column(name="name_package",nullable = false)
    private String name_package;
    @ManyToOne(fetch = FetchType.LAZY) // for relationship package -> package_status (many to one)
    @JoinColumn(name="status_id",referencedColumnName="status_id", insertable=true, updatable=true)
    private StatusPackage statusPackage;
    @ManyToOne(fetch = FetchType.LAZY) // for relationship package -> type_package (many to one)
    @JoinColumn(name="type_package_id",referencedColumnName="type_id", insertable=true, updatable=true)
    private TypePackage typePackage;
    @ManyToOne(fetch = FetchType.LAZY) // for relationship package -> offices (many to one)
    @JoinColumn(name="office_id",referencedColumnName="office_id", insertable=true, updatable=true)
    private Office office;
    @ManyToOne(fetch = FetchType.LAZY) // for relationship package -> courier (many to one)
    @JoinColumn(name="courier_id",referencedColumnName="courier_id", insertable=true, updatable=true)
    private Courier courier;
    @ManyToOne(fetch = FetchType.LAZY) // for relationship package -> courier (many to one)
    @JoinColumn(name="user_id",referencedColumnName="user_id", insertable=true, updatable=true)
    private Customer customer;
    @Column(name="weight_package",nullable = true)
    private BigDecimal weight_package;
    @Column(name="size_height",nullable = true)
    private Integer size_height;
    @Column(name="size_width",nullable = true)
    private Integer size_width;
    @Column(name = "package_price",nullable = true)
    private Double package_price;
    @Generated( value = GenerationTime.ALWAYS )
    @Column(name = "total_cost", insertable = false, nullable = true)
    private Double total_cost;
    @Column(name = "date_register_package",nullable = true)
    private Date date_register_package;
    @Column(name = "date_delivery_package",nullable = true)
    private Date date_delivery_package;
    @ManyToOne(fetch = FetchType.LAZY) // for relationship package -> courier (many to one)
    @JoinColumn(name="receiver_id",referencedColumnName="user_id", insertable=true, updatable=true)
    private Customer receiver;
    @Column(name="review_package",nullable = true)
    @JsonIgnore
    private Boolean review_package;
    @OneToOne(mappedBy = "packages_problem", fetch = FetchType.LAZY/*,cascade = CascadeType.ALL*/)
    @JsonIgnore
    private PackageProblem packageProblem;


    public Packages() {

    }

    public Packages(Long package_id, String name_package, StatusPackage statusPackage, TypePackage typePackage, Office office, Courier courier) {
        this.package_id = package_id;
        this.name_package = name_package;
        this.statusPackage = statusPackage;
        this.typePackage = typePackage;
        this.office = office;
        this.courier = courier;
    }

    public Packages(Long package_id, String name_package, StatusPackage statusPackage, TypePackage typePackage, Office office) {
        this.package_id = package_id;
        this.name_package = name_package;
        this.statusPackage = statusPackage;
        this.typePackage = typePackage;
        this.office = office;
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

    public StatusPackage getStatusPackage() {
        return statusPackage;
    }

    public void setStatusPackage(StatusPackage statusPackage) {
        this.statusPackage = statusPackage;
    }

    public TypePackage getTypePackage() {
        return typePackage;
    }

    public void setTypePackage(TypePackage typePackage) {
        this.typePackage = typePackage;
    }


    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getWeight_package() {
        return weight_package;
    }

    public void setWeight_package(BigDecimal weight_package) {
        this.weight_package = weight_package;
    }

    public int getSize_height() {
        return size_height;
    }

    public void setSize_height(int size_height) {
        this.size_height = size_height;
    }

    public int getSize_width() {
        return size_width;
    }

    public void setSize_width(int size_width) {
        this.size_width = size_width;
    }

    public boolean isReview_package() {
        return review_package;
    }

    public void setReview_package(boolean review_package) {
        this.review_package = review_package;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Customer getReceiver() {
        return receiver;
    }

    public void setReceiver(Customer receiver) {
        this.receiver = receiver;
    }

    public Double getPackage_price() {
        return package_price;
    }

    public void setPackage_price(Double package_price) {
        this.package_price = package_price;
    }

    public Double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(Double total_cost) {
        this.total_cost = total_cost;
    }

    public Date getDate_register_package() {
        return date_register_package;
    }

    public void setDate_register_package(Date date_register_package) {
        this.date_register_package = date_register_package;
    }

    public Date getDate_delivery_package() {
        return date_delivery_package;
    }

    public void setDate_delivery_package(Date date_delivery_package) {
        this.date_delivery_package = date_delivery_package;
    }



}
