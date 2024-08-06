package com.example.demo.controllers;

import com.example.demo.constants.CacheConstraints;
import com.example.demo.exceptions.global.ObjectNotFoundException;
import com.example.demo.models.comparators.PackageIDNumberComparator;
import com.example.demo.models.dtos.CourierDto;
import com.example.demo.models.dtos.CustomerDto;
import com.example.demo.models.dtos.PackageDto;
import com.example.demo.models.entity.*;
import com.example.demo.private_lib.ErrorHandler;
import com.example.demo.private_lib.cache_checking.CacheChecker;
import com.example.demo.services.CourierService;
import com.example.demo.services.CustomerService;
import com.example.demo.services.Impl.AdministratorServiceImpl;
import com.example.demo.utils.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    private AdministratorServiceImpl administratorService;

    private final CustomerService customerService;

    private final CourierService courierService;

    private final ModelMapper mapper;

    private Administrator result;

    private final CacheChecker cacheChecker;

    public AdministratorController(CustomerService customerService, CourierService courierServiceImpl, CacheChecker cacheChecker) {
        this.customerService = customerService;
        this.courierService = courierServiceImpl;
        this.mapper = ObjectMapper.getMapperInstance();
        this.cacheChecker = cacheChecker;
    }

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

    @GetMapping(value = "/customers/{id}", produces = "application/json")
    @PreAuthorize("@administratorServiceImpl.isOwner(authentication.principal.username, #adminId)")
    public ResponseEntity<List<CustomerDto>> getCustomers(@PathVariable("id") Long adminId, Authentication authentication) throws Exception {
        try {
            List<Customer> customerList = new ArrayList<>(customerService.getAllCustomers());

            Collections.sort(customerList);

            List<CustomerDto> customers = ObjectMapper.map(customerList,CustomerDto.class);

            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/couriers/{id}", produces = "application/json")
    @PreAuthorize("@administratorServiceImpl.isOwner(authentication.principal.username, #adminId)")
    public ResponseEntity<List<CourierDto>> getCouriers(@PathVariable("id") Long adminId, Authentication authentication) {
        try {

            List<Courier> courierList = new ArrayList<>(courierService.getAllCouriers());

            Collections.sort(courierList);

            TypeMap<Courier,CourierDto> typeMap = ObjectMapper.getTypeMapInstance(Courier.class,CourierDto.class);

            typeMap.addMapping(Courier::getCourier_first_name,CourierDto::setFirstName);
            typeMap.addMapping(Courier::getCourier_last_name,CourierDto::setLastName);
            typeMap.addMapping(Courier::getCourier_city_name,CourierDto::setCity);

            List<CourierDto> couriers = ObjectMapper.map(courierList,typeMap);
            // to do filter by phone
            return new ResponseEntity<>(couriers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/courier/create", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> insertCourier(@RequestBody @Valid CourierDto courierDto, BindingResult bindingResult) throws ValidationException {

        if(bindingResult.hasErrors()){

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Courier courier = ObjectMapper.map(courierDto,Courier.class);

        administratorService.Insert(courier);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/customer/create", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> insertCustomer(@RequestBody @Valid CustomerDto customerDto, BindingResult bindingResult) throws ValidationException {

        if(bindingResult.hasErrors()){

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Customer customer = ObjectMapper.map(customerDto,Customer.class);

        administratorService.Insert(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/courier/update", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> updateCourier(@RequestBody @Valid CourierDto courierDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(bindingResult), HttpStatus.BAD_REQUEST);
        }
        Courier courier = ObjectMapper.map(courierDto,Courier.class);

        Courier updatedCourier = administratorService.updateCourier(courier);

        return new ResponseEntity<>(updatedCourier,HttpStatus.OK);

    }

    @DeleteMapping(value = "/customers/delete/{user_id}")
    public ResponseEntity<Integer> deleteCustomer(@PathVariable(value = "user_id") int user_id) {
        Optional<Customer> isCustomerExists = administratorService.findCustomerById(user_id);
        if(isCustomerExists.isPresent()){
            int res = administratorService.deleteCustomerById(user_id);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }else{
            throw new ObjectNotFoundException("customer with id "+user_id);
        }
    }

    @DeleteMapping(value = "/couriers/delete/{username}/{phone}")
    public ResponseEntity<Integer> deleteCourier(@PathVariable(value = "username") String username, @PathVariable(value = "phone") String phone) {
        Optional<Courier> isExistsCourier = administratorService.findCourierByUsername(username);
        if(isExistsCourier.isPresent()){
            int result = administratorService.deleteCourierByUsernamePhone(username, phone);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }else{
            throw new ObjectNotFoundException("courier with username "+username);
        }
    }

    @GetMapping(value = "/types")
    public ResponseEntity<?> getAllTypes() {
        List<TypePackage> typePackageList = administratorService.getAllPackagesTypes();
        System.out.println(this.cacheChecker.getFromCache(CacheConstraints.ADMINISTRATOR_CACHE_NAME));
        if (typePackageList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(typePackageList, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/cities")
    public ResponseEntity<?> getAllCities() {
        List<City> cityList = customerService.getAllCities();
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
    public ResponseEntity<PackageDto> registerPackage(@RequestBody Packages packages) {

        Packages result = administratorService.registerPackage(packages);

        TypeMap<Packages,PackageDto> typeMap = ObjectMapper.getTypeMapInstance(Packages.class,PackageDto.class);

        typeMap.addMapping(Packages::getPackage_price, PackageDto::setPackagePrice);
        typeMap.addMapping(Packages::getCustomer, PackageDto::setFromCustomer);
        typeMap.addMapping(Packages::getReceiver, PackageDto::setToCustomer);
        typeMap.addMapping(Packages::isReview_package, PackageDto::setPackageReview);

        PackageDto insertedPackage = typeMap.map(result);

        return new ResponseEntity<>(insertedPackage, HttpStatus.OK);
    }

    @GetMapping(value = "/packages")
    public ResponseEntity<List<PackageDto>> getAllPackages() throws Exception {
        List<Packages> packagesList = administratorService.getAllPackages();

        PackageIDNumberComparator idNumberComparator = new PackageIDNumberComparator();
        packagesList.sort(idNumberComparator);

        TypeMap<Packages, PackageDto> typeMap = ObjectMapper.getTypeMapInstance(Packages.class,PackageDto.class);

        typeMap.addMapping(Packages::getPackage_price, PackageDto::setPackagePrice);
        typeMap.addMapping(Packages::getCustomer, PackageDto::setFromCustomer);
        typeMap.addMapping(Packages::getReceiver, PackageDto::setToCustomer);
        typeMap.addMapping(Packages::isReview_package, PackageDto::setPackageReview);

        List<PackageDto> shipmentsDto = ObjectMapper.map(packagesList, typeMap);

        if (packagesList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(shipmentsDto, HttpStatus.OK);
        }
    }


}
