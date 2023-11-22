package com.example.demo.services;

import com.example.demo.models.entity.Courier;
import com.example.demo.models.entity.Customer;
import com.example.demo.models.entity.Packages;
import com.example.demo.models.entity.TypePackage;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public interface AdministratorService {
    Packages registerPackage(Packages packages);
    List<Packages> getAllPackages() throws Exception;
    List<Packages> findPackagesByCustomerPhone(String phone);
    List<TypePackage> getAllPackagesTypes();
    Optional<Customer> findCustomerById(int customerId);
    Optional<Courier> findCourierByUsername(String username);
    int deleteCustomerById(int id);
    int deleteCourierByUsernamePhone(String username, String phone);
    Courier updateCourier(Courier courier);
    boolean isOwner(String username, Long adminId);
}
