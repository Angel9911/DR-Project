package com.example.demo.controllers;

import com.example.demo.models.entity.Courier;
import com.example.demo.models.entity.PackageProblem;
import com.example.demo.models.entity.Packages;
import com.example.demo.services.Impl.CourierServiceImpl;
import com.example.demo.utils.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/courier")
public class CourierController {


    private final CourierServiceImpl courierServiceImpl;
    private final ModelMapper mapper;

    @Autowired
    public CourierController(CourierServiceImpl courierServiceImpl) {
        this.courierServiceImpl = courierServiceImpl;
        this.mapper = ObjectMapper.getMapperInstance();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<String>> getCourierPackages() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Courier> LoginCourier(@RequestParam(value = "username") String username) {
        try {
            Courier result = courierServiceImpl.Login(username);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ValidationException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/package/update", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> updatePackageByStatus(@RequestParam(value = "packageId") int packageId, @RequestParam(value = "status") String status) {
        try {
            courierServiceImpl.updatePackageByStatus(packageId, status);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ValidationException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/package/problem/update")
    public ResponseEntity<String> updateProblemPackage(@RequestParam(value = "packageId") int packageId, @RequestParam(value = "status") String status, @RequestParam(value = "message") String message_problem, @RequestBody MultipartFile file) {
        try {
            //courierServiceImpl.updateProblemPackage(packageId, status, message_problem);
            System.out.println(file.getInputStream());
            System.out.println(file.getOriginalFilename());
            String response = courierServiceImpl.uploadFileInS3bucket(file);
            if(!response.isEmpty()){
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ValidationException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/packages", produces = "application/json")
    public ResponseEntity<List<Packages>> getCourierPackages(@RequestParam(value = "username") String username) throws Exception {
        System.out.println(username);
        System.out.println("test2");
        try {
            List<Packages> packagesList = courierServiceImpl.getCourierPackages(username);
            return new ResponseEntity<>(packagesList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/packages/problem", produces = "application/json")
    public ResponseEntity<List<PackageProblem>> getCourierProblemPackages(@RequestParam(value = "username") String username) throws Exception {
        try {
            List<PackageProblem> packagesList = courierServiceImpl.getCourierProblemPackages(username);
            return new ResponseEntity<>(packagesList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/packages/delivered", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Packages>> getDeliveredPackages(@RequestParam(value = "username") String username) {
        try {
            List<Packages> packagesList = courierServiceImpl.getDeliveredPackages(username);
            return new ResponseEntity<>(packagesList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
