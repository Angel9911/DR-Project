package com.example.demo.models.dtos;

import com.example.demo.models.entity.Customer;

public class PackageDto {

    private String packageName;

    private String packageStatus;

    private String packageType;

    private Double packagePrice;

    private Customer fromCustomer;

    private Customer toCustomer;

    private Boolean packageReview;
}
