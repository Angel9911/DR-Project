package com.example.demo.controllers;

import com.example.demo.constants.CacheConstraints;
import com.example.demo.exceptions.global.ObjectNotValidException;
import com.example.demo.models.comparators.PackagePriceComparator;
import com.example.demo.models.dtos.CustomerDto;
import com.example.demo.models.dtos.PackageDto;
import com.example.demo.models.entity.City;
import com.example.demo.models.entity.Customer;
import com.example.demo.models.entity.Packages;
import com.example.demo.models.views.CustomerView;
import com.example.demo.private_lib.ErrorHandler;
import com.example.demo.private_lib.cache_checking.CacheChecker;
import com.example.demo.services.CustomerService;
import com.example.demo.utils.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    private final ModelMapper modelMapper;

    private final CacheChecker cacheChecker;

    @Autowired
    public CustomerController(CustomerService customerService, CacheChecker cacheChecker) {
        this.customerService = customerService;
        this.modelMapper = ObjectMapper.getMapperInstance();
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.cacheChecker = cacheChecker;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Customer> insertCustomer(@RequestBody CustomerDto customerDto) {
        try {
            TypeMap<CustomerDto,Customer> typeMap= ObjectMapper.getTypeMapInstance(CustomerDto.class,Customer.class);

            typeMap.addMapping(CustomerDto::getFirstName,Customer::setName);
            typeMap.addMapping(CustomerDto::getLastName,Customer::setLastName);

            Customer customer = ObjectMapper.map(customerDto,Customer.class);
            customerService.Insert(customer);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (Exception e) {
            throw new ObjectNotValidException(" customer ");
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @PreAuthorize("@customerServiceImpl.isOwner(authentication.principal.username, #id)")
    public ResponseEntity<CustomerView> LoginCustomer(@PathVariable(value = "id") Long id, Authentication authentication) {
        try {
            CustomerView result = customerService.Login(id);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ValidationException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DO TUK E KACHENO V DOKUMENTACIQTA
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerDto customerDto, BindingResult bindingResult) {
        // to do - implement validation using bindingresult
        if(bindingResult.hasErrors()){

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(bindingResult),HttpStatus.BAD_REQUEST);
        }
        TypeMap<CustomerDto,Customer> typeMap = ObjectMapper.getTypeMapInstance(CustomerDto.class,Customer.class);

        typeMap.addMapping(CustomerDto::getFirstName,Customer::setName);
        typeMap.addMapping(CustomerDto::getLastName,Customer::setLastName);

        Customer customer = typeMap.map(customerDto);

        if (customer.getUserId() != null) {
            try {
                Customer result = customerService.Update(customer);

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
            Optional<Customer> result = customerService.IsEmailExist(email);
            if (result.isPresent()) {
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
        List<City> listCities = new ArrayList<>(customerService.getAllCities());
        List<String> cityNames = new ArrayList<>();
        for (City city : listCities) {
            cityNames.add(city.getCity_name());
        }

        return new ResponseEntity<>(cityNames, HttpStatus.OK);
    }

    @RequestMapping(value = "/city/{name}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<City> GetCityIdByName(@PathVariable(value = "name") String name) {
        City city = new City();
        city.setCity_id(customerService.getCityIdByName(name).getCity_id());
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @RequestMapping(value = "/packages", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<PackageDto>> getCustomerPackages(@RequestParam(value = "username") String username) throws Exception {
        try {

            List<Packages> packagesList = customerService.getAllPackages(username);

            PackagePriceComparator priceComparator = new PackagePriceComparator();

            packagesList.sort(priceComparator);

            TypeMap<Packages, PackageDto> typeMap = ObjectMapper.getTypeMapInstance(Packages.class,PackageDto.class);

            typeMap.addMapping(Packages::getPackage_price, PackageDto::setPackagePrice);
            typeMap.addMapping(Packages::getCustomer, PackageDto::setFromCustomer);
            typeMap.addMapping(Packages::getReceiver, PackageDto::setToCustomer);
            typeMap.addMapping(Packages::isReview_package, PackageDto::setPackageReview);

            List<PackageDto> shipmentsDto = ObjectMapper.map(packagesList, typeMap);
            return new ResponseEntity<>(shipmentsDto, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
