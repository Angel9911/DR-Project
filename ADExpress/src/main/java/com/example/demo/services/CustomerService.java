package com.example.demo.services;

import com.example.demo.models.entity.City;
import com.example.demo.models.entity.Customer;
import com.example.demo.models.entity.Packages;
import com.example.demo.models.views.CustomerView;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;

@Service
public interface CustomerService {
    CustomerView Login(Long customerId) throws ValidationException;
    CustomerView getCustomerDetails(String username)throws ValidationException;
    void Insert(Object object);
    Customer IsEmailExist(String email);
    List<Customer> getAllCustomers() throws Exception;
    List<Packages> getAllPackages(String username) throws Exception;
    List<City> getAllCities();
    City getCityIdByName(String name);
    Customer Update(Customer customer);
    boolean isOwner(String username, Long customerId);
}
