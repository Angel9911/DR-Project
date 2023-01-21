package com.example.customerservice.services;

import com.example.customerservice.entities.Customer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${package.service.endpoint}")
    private String packageServiceEndPoint;

    private final List<Customer> packagesArrayList = Arrays.asList(
            new Customer(1L,"angel","angelov"),
            new Customer(2L,"ivan","ivanov"),
            new Customer(3L,"Petur","petrov")
    );

    public List<Customer> getCustomers(){
        return packagesArrayList;
    }

    @CircuitBreaker(name="packageServiceEndPoint", fallbackMethod = "fallBack")
    public Customer getCustomerPackages(){
        Customer customer = packagesArrayList.get(0);

        List getCustomerPackages = (List) this.restTemplate.getForEntity(this.packageServiceEndPoint,List.class).getBody();

        customer.setListpackages(getCustomerPackages);

        return customer;
    }
    public Customer fallBack(Throwable throwable){
        Customer customer = packagesArrayList.get(0);

        return customer;
    }

}
