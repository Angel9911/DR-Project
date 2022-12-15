package com.example.demo.services;

import com.example.demo.model.*;
import com.example.demo.private_lib.PackageHandler;
import com.example.demo.private_lib.User;
import com.example.demo.repository.CourierRepository;
import com.example.demo.repository.PackageProblemRepository;
import com.example.demo.repository.PackageRepository;
import com.example.demo.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.util.*;

@Service
public class CourierService extends User {
    @Autowired
    CourierRepository courierRepository;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    PackageProblemRepository packageProblemRepository;
   // @Autowired
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
            return PackageHandler.getPackageList(getPackages);
        } else {
            throw new Exception("error");
        }
    }
    @Transactional
    public List<PackageProblem> getCourierProblemPackages(String username) throws Exception {
        if (!username.isEmpty()) {
            List<PackageProblem> getPackages = packageProblemRepository.findProblemCourierPackagesByUsername(username);
            if(!getPackages.isEmpty()){
                List<Object> objectList = new ArrayList<Object>(getPackages);
                return PackageHandler.getCourierProblemPackages(objectList);
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
            return PackageHandler.getPackageList(getPackages);
        } else {
            throw new Exception("error");
        }
    }

    @Transactional
    @Override
    public Courier Login(String username) throws ValidationException {
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

    @Override
    public Courier Update(Object object) {
        return null;
    }

    @Override
    public void Insert(Object object) {

    }

}
