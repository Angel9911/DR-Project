package com.example.demo.services;

import com.example.demo.models.City;
import com.example.demo.models.Customer;
import com.example.demo.models.Packages;

import java.util.List;

public interface CustomerService {
    Customer IsEmailExist(String email);
    List<Customer> getAllCustomers() throws Exception;
    List<Packages> getAllPackages(String username) throws Exception;
    List<City> getAllCities();
    City getCityIdByName(String name);
}
