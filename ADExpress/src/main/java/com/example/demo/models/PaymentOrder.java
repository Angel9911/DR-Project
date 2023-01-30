package com.example.demo.models;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Bean;

@Data
@ToString
public class PaymentOrder {
    private String package_name;
    private Long package_id;
    private String shipping;
    private double total_price;
    private double tax_price;

    public PaymentOrder(String package_name, Long package_id, String shipping, double total_price, double tax_price) {
        this.package_name = package_name;
        this.package_id = package_id;
        this.shipping = shipping;
        this.total_price = total_price;
        this.tax_price = tax_price;
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

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public double getTax_price() {
        return tax_price;
    }

    public void setTax_price(double tax_price) {
        this.tax_price = tax_price;
    }
}
