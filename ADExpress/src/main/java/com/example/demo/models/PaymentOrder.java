package com.example.demo.models;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Bean;

@Data
@ToString
public class PaymentOrder {
    private String customer_username;
    private String package_name;
    private Long package_id;
    private String package_type;
    private float total_price;
    private float package_price;
    private float shipping;
    private float tax_price;

    /*public PaymentOrder(String package_name, Long package_id, String shipping, String total_price, String tax_price) {
        this.package_name = package_name;
        this.package_id = package_id;
        this.shipping = shipping;
        this.total_price = total_price;
        this.tax_price = tax_price;
    }*/

    public String getCustomer_username() {
        return customer_username;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public Long getPackage_id() {
        return package_id;
    }

    public void setPackage_id(Long package_id) {
        this.package_id = package_id;
    }

    public float getShipping() {
        return shipping;
    }

    public void setShipping(float shipping) {
        this.shipping = shipping;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public float getTax_price() {
        return tax_price;
    }

    public void setTax_price(float tax_price) {
        this.tax_price = tax_price;
    }

    public String getPackage_type() {
        return package_type;
    }

    public void setPackage_type(String package_type) {
        this.package_type = package_type;
    }

    public float getPackage_price() {
        return package_price;
    }

    public void setPackage_price(float package_price) {
        this.package_price = package_price;
    }



}
