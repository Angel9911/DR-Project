package com.example.demo.models.views;

import com.example.demo.models.entity.Customer;
import org.springframework.data.rest.core.config.Projection;

// projection
@Projection(name = "customerView", types = Customer.class)
public interface CustomerView {

    String getName();
    String getLastName();
    String getCity();
    String getEmail();
    String getAddress();
    String getPhone();
    User_accountView getAccount();
}
