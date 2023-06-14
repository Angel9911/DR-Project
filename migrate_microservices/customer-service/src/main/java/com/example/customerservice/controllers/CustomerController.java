package com.example.customerservice.controllers;

import com.example.customerservice.entities.Customer;
import com.example.customerservice.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/customers", produces = "application/json")
    public ResponseEntity<List<Customer>> getCustomers(){
        List<Customer> customerList = this.customerService.getCustomers();
        return new ResponseEntity<List<Customer>>(customerList,HttpStatus.OK);
    }

    @GetMapping(value="/customers/packages", produces = "application/json")
    public ResponseEntity<Customer> getCustomerPackages() throws InterruptedException {
        Customer customer = this.customerService.getCustomerPackages();
        return new ResponseEntity<Customer>(customer,HttpStatus.OK);

    }

    //test
    @GetMapping(value="/test", produces = "application/json")
    public String getStores() {
        throw new RuntimeException("Not Available");
    }

    public String defaultStores() {
        return "problem";
    }
}
