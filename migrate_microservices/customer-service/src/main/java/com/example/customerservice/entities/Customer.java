package com.example.customerservice.entities;

import java.util.List;

public class Customer {
    private Long customer_id;
    //@Column(name="name_package",nullable = false)
    private String name;
    private String lastname;
    private List<Object> listpackages;

    public Customer(Long customer_id, String name, String lastname) {
        this.customer_id = customer_id;
        this.name = name;
        this.lastname = lastname;
    }

    public Customer(Long customerId) {
        this.customer_id = customerId;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public List<Object> getListpackages() {
        return listpackages;
    }

    public void setListpackages(List<Object> listpackages) {
        this.listpackages = listpackages;
    }


}
