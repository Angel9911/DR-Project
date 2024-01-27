package com.example.demo.models;

import javax.validation.constraints.NotNull;

public class Address {

    private String address_text;

    private String address_id;

    public Address(String address_text, String address_id) {
        this.address_text = address_text;
        this.address_id = address_id;
    }

    public Address() {

    }

    public String getAddress_text() {
        return address_text;
    }

    public void setAddress_text(String address_text) {
        this.address_text = address_text;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }
}
