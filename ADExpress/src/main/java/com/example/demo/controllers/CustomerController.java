package com.example.demo.controllers;

import com.example.demo.models.City;
import com.example.demo.models.Customer;
import com.example.demo.models.Packages;
import com.example.demo.services.Impl.CustomerServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/customer")
public class CustomerController {
    //@Autowired
    private final CustomerServiceImpl customerServiceImpl;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    public CustomerController(CustomerServiceImpl customerServiceImpl) {
        this.customerServiceImpl = customerServiceImpl;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Customer> insertCustomer(@RequestBody Customer customer) {
        try {
            customerServiceImpl.Insert(customer);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = "application/json")
    // @PreAuthorize("hasRole('user')")
    public ResponseEntity<Customer> LoginCustomer(@PathVariable(value = "username") String username) {
        try {
            Customer result = customerServiceImpl.Login(username);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ValidationException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DO TUK E KACHENO V DOKUMENTACIQTA
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        if (customer.getUser_id() != null) {
            try {
                Customer result = customerServiceImpl.Update(customer);

                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (EntityNotFoundException e) {
                return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);

        }
    }

    @GetMapping(produces = "application/json") // http://localhost:8082/customer?email=email2@example.com
    public ResponseEntity<Boolean> findEmail(@RequestParam(value = "email") String email) {
        if (email != null) {
            Customer result = customerServiceImpl.IsEmailExist(email);
            if (result != null) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.OK);
            }

        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/city", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<String>> getAllExamples() {
        List<City> listCities = new ArrayList<>(customerServiceImpl.getAllCities());
        List<String> cityNames = new ArrayList<>();
        for (City city : listCities) {
            cityNames.add(city.getCity_name());
        }
        //exampleRepository.findAll();
        return new ResponseEntity<>(cityNames, HttpStatus.OK);
    }

    @RequestMapping(value = "/city/{name}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<City> GetCityIdByName(@PathVariable(value = "name") String name) {
        City city = new City();
        city.setCity_id(customerServiceImpl.getCityIdByName(name).getCity_id());
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @RequestMapping(value = "/packages", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Packages>> getCustomerPackages(@RequestParam(value = "username") String username) throws Exception {
        try {
            System.out.println("Request received to the controller");
            CaffeineCache caffeineCache = (CaffeineCache)cacheManager .getCache("customer");
            Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();

            for (Map.Entry<Object, Object> entry : nativeCache.asMap().entrySet()) {
                System.out.println("Key = " + entry.getKey());
                System.out.println("Value = " + entry.getValue());
            }
            List<Packages> packagesList = customerServiceImpl.getAllPackages(username);
            return new ResponseEntity<>(packagesList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
