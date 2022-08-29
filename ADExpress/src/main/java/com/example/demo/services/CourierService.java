package com.example.demo.services;

import com.example.demo.model.*;
import com.example.demo.repository.CourierRepository;
import com.example.demo.repository.PackageProblemRepository;
import com.example.demo.repository.PackageRepository;
import com.example.demo.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Ssl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CourierService {
    @Autowired
    CourierRepository courierRepository;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    PackageProblemRepository packageProblemRepository;

    @Transactional
    public Courier LoginCourierByUsernamePassword(String username) throws ValidationException {
        Courier courier = courierRepository.findCourierByUsernameAndPassword(username);
        if (courier != null) {
            Courier res = new Courier();
            res.setCourier_first_name(courier.getCourier_first_name());
            res.setCourier_last_name(courier.getCourier_last_name());
            return res;
        } else {
            throw new ValidationException("error");
        }
    }

    @Transactional
    public List<Courier> getAllCouriers() {
        return courierRepository.findAll();
    }

    @Transactional
    public void updatePackageByStatus(int packageId, String status) throws ValidationException {
        int statusId = statusRepository.findStatusIdByStatusType(status);
        if (packageId != 0 && statusId != 0) {
            System.out.println(packageId + "" + statusId);
            LocalDate getCurrentDate = java.time.LocalDate.now();
            java.sql.Date getDate = java.sql.Date.valueOf(getCurrentDate);
            packageRepository.updateStatusPackage(statusId, packageId, getDate);
        } else {
            throw new ValidationException("error");
        }
    }

    @Transactional
    public void updateProblemPackage(int packageId, String status, String message_problem) throws ValidationException {
        int statusId = statusRepository.findStatusIdByStatusType(status);
        if (packageId != 0 && statusId != 0) {
            System.out.println(packageId + "" + statusId);
            packageRepository.updateStatusPackageProblem(statusId, packageId);
            PackageProblem packageProblem = new PackageProblem();
            System.out.println(packageRepository.getPackageByPackageId(packageId));
            packageProblem.setPackages_problem(packageRepository.getPackageByPackageId(packageId));
            packageProblem.setMessage(message_problem);
            packageProblemRepository.save(packageProblem);
        } else {
            throw new ValidationException("error");
        }
    }

    @Transactional
    public List<Packages> getCourierPackages(String username) throws Exception {
        if (!username.isEmpty()) {
            List<Packages> getPackages = packageRepository.findCourierPackagesByUsername(username);
            return getPackagesList(getPackages);
        } else {
            throw new Exception("error");
        }
    }
    @Transactional
    public List<PackageProblem> getCourierProblemPackages(String username) throws Exception {
        if (!username.isEmpty()) {
            List<PackageProblem> getPackages = packageProblemRepository.findProblemCourierPackagesByUsername(username);
            List<PackageProblem> resultPackages = new ArrayList<>();
            if(!getPackages.isEmpty()){
                for (PackageProblem packageProblem:getPackages) {
                    PackageProblem getPackageProblem = new PackageProblem();
                    Packages packages = new Packages();
                    packages.setPackage_id(packageProblem.getPackages_problem().getPackage_id());
                    packages.setName_package(packageProblem.getPackages_problem().getName_package());
                    TypePackage typePackage = new TypePackage();
                    typePackage.setType_name(packageProblem.getPackages_problem().getTypePackage().getType_name());
                    packages.setTypePackage(typePackage);
                    Customer customer = new Customer();
                    customer.setName(packageProblem.getPackages_problem().getCustomer().getName());
                    customer.setLast_name(packageProblem.getPackages_problem().getCustomer().getLast_name());
                    customer.setAddress(packageProblem.getPackages_problem().getCustomer().getAddress());
                    customer.setPhone(packageProblem.getPackages_problem().getCustomer().getPhone());
                    packages.setCustomer(customer);
                    packages.setSize_height(0);
                    packages.setSize_width(0);
                    packages.setReview_package(false);
                    packages.setPackage_price(packageProblem.getPackages_problem().getPackage_price());
                    System.out.println(packageProblem.getPackages_problem().getTotal_cost());
                    packages.setTotal_cost(packageProblem.getPackages_problem().getTotal_cost());
                    Date registerDate = this.getDate(packageProblem.getPackages_problem().getDate_register_package());
                    packages.setDate_register_package(registerDate);
                    getPackageProblem.setPackages_problem(packages);
                    getPackageProblem.setMessage(packageProblem.getMessage());

                    resultPackages.add(getPackageProblem);
                }
                return resultPackages;
            }else {
                throw new Exception("error");
            }
        } else {
            throw new Exception("error");
        }
    }
    @Transactional
    public List<Packages> getDeliveredPackages(String username) throws Exception {
        if (!username.isEmpty()) {
            List<Packages> getPackages = packageRepository.findDeliveredCourierPackagesByUsername(username);
            return getPackagesList(getPackages);
        } else {
            throw new Exception("error");
        }
    }

    public List<Packages> getPackagesList(List<Packages> getPackages) throws Exception {
        if (getPackages.isEmpty()) {
            throw new Exception("error");
        } else {
            List<Packages> resultPackages = new ArrayList<>();
            System.out.println(getPackages.size());
            for (Packages packages : getPackages) {
                Packages getPackage = new Packages();
                System.out.println(packages.getCustomer().getAddress());
                getPackage.setPackage_id(packages.getPackage_id());
                getPackage.setName_package(packages.getName_package());
                TypePackage typePackage = new TypePackage();
                typePackage.setType_name(packages.getTypePackage().getType_name());
                getPackage.setTypePackage(typePackage);
                Customer customer = new Customer();
                customer.setName(packages.getCustomer().getName());
                customer.setLast_name(packages.getCustomer().getLast_name());
                customer.setAddress(packages.getCustomer().getAddress());
                customer.setPhone(packages.getCustomer().getPhone());
                getPackage.setCustomer(customer);
                StatusPackage statusPackage = new StatusPackage();
                statusPackage.setStatus_type(packages.getStatusPackage().getStatus_type());
                getPackage.setStatusPackage(statusPackage);
                packages.setSize_height(0);
                packages.setSize_width(0);
                packages.setReview_package(false);
                getPackage.setSize_height(packages.getSize_height());
                getPackage.setSize_width(packages.getSize_width());
                getPackage.setPackage_price(packages.getPackage_price());
                System.out.println(packages.getTotal_cost());
                getPackage.setTotal_cost(packages.getTotal_cost());
                if(packages.getDate_register_package()!=null){
                    Date registerDate = this.getDate(packages.getDate_register_package());
                    getPackage.setDate_register_package(registerDate);
                }
                if(packages.getDate_delivery_package()!=null){
                    Date deliveryPackage = this.getDate(packages.getDate_delivery_package());
                    getPackage.setDate_delivery_package(deliveryPackage);
                }
                packages.setReview_package(packages.isReview_package());
                /*
                don't need these values
                 */
                resultPackages.add(getPackage);
            }
            return resultPackages;
        }
    }
    private Date getDate(Date datePackage){
        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String packageDate = outputFormatter.format(datePackage); // Output : 01/20/2012
        System.out.println(packageDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate localdate = LocalDate.parse(packageDate, formatter);
        return java.sql.Date.valueOf(localdate);
    }
}
