package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.services.Impl.AdministratorServiceImpl;
import com.example.demo.services.Impl.CourierServiceImpl;
import com.example.demo.services.Impl.CustomerServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    private AdministratorServiceImpl administratorService;
    @Autowired
    private CacheManager cacheManager;
    //@Autowired
    private CustomerServiceImpl customerServiceImpl;
   // @Autowired
    private CourierServiceImpl courierServiceImpl;
    private Administrator result;

    // @PreAuthorize("hasRole('administrator')")
    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = "application/json")//check
    public ResponseEntity<Administrator> LoginAministrator(@PathVariable(value = "username") String username) throws ValidationException {
        try {
            this.result = administratorService.Login(username);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ValidationException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/customers", produces = "application/json") //check
    public ResponseEntity<List<Customer>> getCustomers() throws Exception {
        try {
            List<Customer> resultList = new ArrayList<>(customerServiceImpl.getAllCustomers());
            return new ResponseEntity<>(resultList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/couriers", produces = "application/json")//check
    public ResponseEntity<List<Courier>> getCouriers() {
        try {
            List<Courier> customerList = new ArrayList<>(courierServiceImpl.getAllCouriers());
            return new ResponseEntity<>(customerList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DO TUK E KACHENO V DOKUMENTACIQTA
    @RequestMapping(value = "/courier/create", method = RequestMethod.POST, produces = "application/json")
    // bi trqbvalo da bachka
    public ResponseEntity<Courier> insertCourier(@RequestBody @Valid Courier courier) throws ValidationException {
        administratorService.Insert(courier);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/customer/create", method = RequestMethod.POST, produces = "application/json")
    // bi trqbvalo da bachka
    public ResponseEntity<Customer> insertCustomer(@RequestBody Customer customer) throws ValidationException {
        administratorService.Insert(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/courier/update", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Courier> updateCustomer(@RequestBody Courier courier) {
        if (courier.getCourier_id() != null) {
            try {
                Courier result = administratorService.updateCourier(courier);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (EntityNotFoundException e) {
                return new ResponseEntity<Courier>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<Courier>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/customers/delete/{user_id}")
    public ResponseEntity<Integer> deleteCustomer(@PathVariable(value = "user_id") int user_id) {
        int res = administratorService.deleteCustomerById(user_id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping(value = "/couriers/delete/{username}/{phone}")
    public ResponseEntity<Integer> deleteCourier(@PathVariable(value = "username") String username, @PathVariable(value = "phone") String phone) {
        int result = administratorService.deleteCourierByUsernamePhone(username, phone);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/types")
    public ResponseEntity<?> getAllTypes() {
        List<TypePackage> typePackageList = administratorService.getAllPackagesTypes();
        if (typePackageList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(typePackageList, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/cities")
    public ResponseEntity<?> getAllCities() {
        List<City> cityList = customerServiceImpl.getAllCities();
        if (cityList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(cityList, HttpStatus.OK);
        }
    }

    @GetMapping("/package/customer")
    public ResponseEntity<List<Packages>> findPackagesByCustomerPhone(@RequestParam(value = "phone") String phone) {
        ArrayList<Packages> resultPackages = (ArrayList<Packages>) administratorService.findPackagesByCustomerPhone(phone);
        return new ResponseEntity<>(resultPackages, HttpStatus.OK);
    }

    @PostMapping(value = "/package/create", produces = "application/json")
    public ResponseEntity<Packages> registerPackage(@RequestBody Packages packages) {
        Packages result = administratorService.registerPackage(packages);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/packages")
    public ResponseEntity<List<Packages>> getAllPackages() throws Exception {
       /* CaffeineCache caffeineCache = (CaffeineCache)cacheManager .getCache("administrator");
        Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();

        for (Map.Entry<Object, Object> entry : nativeCache.asMap().entrySet()) {
            System.out.println("Key = " + entry.getKey());
            System.out.println("Value = " + entry.getValue());
        } */
        List<Packages> packagesList = administratorService.getAllPackages();
        if (packagesList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(packagesList, HttpStatus.OK);
        }
    }

}
