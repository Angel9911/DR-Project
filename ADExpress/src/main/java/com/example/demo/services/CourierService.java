package com.example.demo.services;

import com.example.demo.models.Courier;
import com.example.demo.models.PackageProblem;
import com.example.demo.models.Packages;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;
import java.util.List;

@Service
public interface CourierService {
    List<Courier> getAllCouriers();
    void updatePackageByStatus(int packageId, String status) throws ValidationException;
    void updateProblemPackage(int packageId, String status, String message_problem) throws ValidationException;
    String uploadFileInS3bucket(MultipartFile file) throws Exception;
    List<Packages> getCourierPackages(String username) throws Exception ;
    List<PackageProblem> getCourierProblemPackages(String username) throws Exception ;
    List<Packages> getDeliveredPackages(String username) throws Exception ;

}
