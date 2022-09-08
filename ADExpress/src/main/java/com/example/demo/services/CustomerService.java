package com.example.demo.services;

import com.example.demo.model.*;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.PackageRepository;
import com.example.demo.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    UserAccountRepository userAccountRepository;
    private User_account user_account;

    @Transactional
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Transactional
    public Customer LoginCustomerByUsername(String username) throws ValidationException {
        Customer customer = customerRepository.findUserByUsernameAndPassword(username);
        Customer res = new Customer();
        if (customer != null) {
            res.setUser_id(customer.getUser_id());
            res.setName(customer.getName());
            res.setLast_name(customer.getLast_name());
            res.setCity(customer.getCity());
            res.setEmail(customer.getEmail());
            res.setAddress(customer.getAddress());
            res.setPhone(customer.getPhone());
            User_account user_account = customer.getUser_account();
            res.setUser_account(user_account);
        } else {
            throw new ValidationException("error");
        }
        return res;
    }

    @Transactional
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer IsEmailExist(String email) {
        return customerRepository.findByEmail(email);
    }

    @Transactional
    public List<Customer> getAllCustomers() throws Exception {
        List<Customer> customerList = customerRepository.findAll();
        if (customerList.isEmpty()) {
            throw new Exception("error");
        } else {
            return customerList;
        }
    }

    @Transactional
    public List<Packages> getAllPackages(String username) throws Exception {
        if (!username.isEmpty()) {
            List<Packages> getPackages = packageRepository.findPackagesByUser_accountUsername(username);
            if (getPackages.isEmpty()) {
                throw new Exception("error");
            } else {
                List<Packages> resultPackages = new ArrayList<>();
                System.out.println(getPackages.size());
                for (Packages packages : getPackages) {
                    System.out.println(packages.getName_package());
                    Packages getPackage = new Packages();
                    getPackage.setName_package(packages.getName_package());
                    TypePackage typePackage = new TypePackage();
                    typePackage.setType_name(packages.getTypePackage().getType_name());
                    getPackage.setTypePackage(typePackage);
                    // if(packages.getOffice().getOffice_id() != null){
                    //      Office office = new Office();
                    // office.setOffice_location(packages.getOffice().getOffice_location());
                    //  getPackage.setOffice(office);
                    //  } else if(packages.getCustomer().getAddress() != null){
                    Customer customer = new Customer();
                    customer.setAddress(packages.getCustomer().getAddress());
                    getPackage.setCustomer(customer);
                    //   }
                    StatusPackage statusPackage = new StatusPackage();
                    statusPackage.setStatus_type(packages.getStatusPackage().getStatus_type());
                    getPackage.setStatusPackage(statusPackage);
                    /*
                    don't need these values
                     */
                    getPackage.setPackage_price(packages.getPackage_price());
                    getPackage.setSize_width(0);
                    getPackage.setSize_height(0);
                    getPackage.setReview_package(false);
                    getPackage.setTotal_cost(packages.getTotal_cost());
                    if (packages.getDate_register_package() != null) {
                        Date registerDate = this.getDate(packages.getDate_register_package());
                        getPackage.setDate_register_package(registerDate);
                    }
                    if (packages.getDate_delivery_package() != null) {
                        Date deliveryPackage = this.getDate(packages.getDate_delivery_package());
                        getPackage.setDate_delivery_package(deliveryPackage);
                    }
                    resultPackages.add(getPackage);
                }
                return resultPackages;
            }
        } else {
            throw new Exception("error");
        }
    }

    @Transactional
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Transactional
    public City getCityIdByName(String name) {
        return cityRepository.findCityNameById(name);
    }

    private Date getDate(Date datePackage) {
        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String packageDate = outputFormatter.format(datePackage); // Output : 01/20/2012
        System.out.println(packageDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate localdate = LocalDate.parse(packageDate, formatter);
        return java.sql.Date.valueOf(localdate);
    }
}
