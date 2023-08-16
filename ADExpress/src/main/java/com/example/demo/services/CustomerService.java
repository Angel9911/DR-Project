package com.example.demo.services;

import com.example.demo.models.entity.City;
import com.example.demo.models.entity.Customer;
import com.example.demo.models.entity.Packages;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    Customer IsEmailExist(String email);
    List<Customer> getAllCustomers() throws Exception;
    List<Packages> getAllPackages(String username) throws Exception;
    List<City> getAllCities();
    City getCityIdByName(String name);
    Customer Update(Customer customer);
}
