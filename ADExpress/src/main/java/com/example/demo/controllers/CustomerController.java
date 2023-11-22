package com.example.demo.controllers;

import com.example.demo.exceptions.global.ObjectNotValidException;
import com.example.demo.models.dtos.CustomerDto;
import com.example.demo.models.entity.City;
import com.example.demo.models.entity.Customer;
import com.example.demo.models.entity.Packages;
import com.example.demo.models.views.CustomerView;
import com.example.demo.services.CustomerService;
import com.example.demo.services.UserAccountService;
import com.example.demo.utils.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    //private final UserAccountService userAccountService;

    private final ModelMapper modelMapper;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        this.modelMapper = ObjectMapper.getMapperInstance();
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
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
            List<String> getAllErrorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(getAllErrorMessages,HttpStatus.BAD_REQUEST);
            // to do
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
        //exampleRepository.findAll();
        return new ResponseEntity<>(cityNames, HttpStatus.OK);
    }

    @RequestMapping(value = "/city/{name}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<City> GetCityIdByName(@PathVariable(value = "name") String name) {
        City city = new City();
        city.setCity_id(customerService.getCityIdByName(name).getCity_id());
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
            List<Packages> packagesList = customerService.getAllPackages(username);
            return new ResponseEntity<>(packagesList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
