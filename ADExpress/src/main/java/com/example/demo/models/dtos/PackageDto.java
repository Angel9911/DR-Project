package com.example.demo.models.dtos;

import com.example.demo.models.entity.Customer;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

public class PackageDto {

    private String packageName;

    private StatusPackageDto statusPackage;

    private TypePackageDto typePackage;

    private Double packagePrice;

    private OfficeDto office;

    private CustomerDto fromCustomer;

    private CustomerDto toCustomer;

    private BigDecimal weightPackage;

    private Integer sizeHeight;

    private Boolean packageReview;

    private Date registerDateShipment;

    private Date deliveryDateShipment;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public StatusPackageDto getStatusPackage() {
        return statusPackage;
    }

    public void setStatusPackage(StatusPackageDto statusPackage) {
        this.statusPackage = statusPackage;
    }

    public TypePackageDto getTypePackage() {
        return typePackage;
    }

    public void setTypePackage(TypePackageDto packageType) {
        this.typePackage = packageType;
    }

    public Double getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(Double packagePrice) {
        this.packagePrice = packagePrice;
    }

    public CustomerDto getFromCustomer() {
        return fromCustomer;
    }

    public void setFromCustomer(CustomerDto fromCustomer) {
        this.fromCustomer = fromCustomer;
    }

    public CustomerDto getToCustomer() {
        return toCustomer;
    }

    public void setToCustomer(CustomerDto toCustomer) {
        this.toCustomer = toCustomer;
    }

    public OfficeDto getOffice() {
        return office;
    }

    public void setOffice(OfficeDto office) {
        this.office = office;
    }

    public BigDecimal getWeightPackage() {
        return weightPackage;
    }

    public void setWeightPackage(BigDecimal weightPackage) {
        this.weightPackage = weightPackage;
    }

    public Integer getSizeHeight() {
        return sizeHeight;
    }

    public void setSizeHeight(Integer sizeHeight) {
        this.sizeHeight = sizeHeight;
    }

    public Boolean getPackageReview() {
        return packageReview;
    }

    public void setPackageReview(Boolean packageReview) {
        this.packageReview = packageReview;
    }

    public Date getRegisterDateShipment() {
        return registerDateShipment;
    }

    public void setRegisterDateShipment(Date registerDateShipment) {
        this.registerDateShipment = registerDateShipment;
    }

    public Date getDeliveryDateShipment() {
        return deliveryDateShipment;
    }

    public void setDeliveryDateShipment(Date deliveryDateShipment) {
        this.deliveryDateShipment = deliveryDateShipment;
    }
}
