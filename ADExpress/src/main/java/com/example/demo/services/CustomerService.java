package com.example.demo.services;

import com.example.demo.models.entity.City;
import com.example.demo.models.entity.Customer;
import com.example.demo.models.entity.Packages;
import com.example.demo.models.views.CustomerView;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public interface CustomerService {
    CustomerView Login(Long customerId) throws ValidationException;
    CustomerView getCustomerDetails(String username)throws ValidationException;
    long getCustomerIdByUsersInfo(String name, String lastName, String phone);
    Optional<Customer> findCustomerById(int customerId);
    void Insert(Object object);
    Optional<Customer> IsEmailExist(String email);
    List<Customer> getAllCustomers() throws Exception;
    List<Packages> getAllPackages(String username) throws Exception;
    List<City> getAllCities();
    City getCityIdByName(String name);
    Customer Update(Customer customer);
    int deleteCustomerByCustomerId(int customerId);
    boolean isOwner(String username, Long customerId);
}
