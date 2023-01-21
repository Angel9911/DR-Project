package com.example.packageservice.controllers;

import com.example.packageservice.entities.Packages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
public class PackageController {
    private final List<Packages> packagesArrayList = Arrays.asList(
            new Packages(1L,"kola"),
            new Packages(2L,"chanta"),
            new Packages(3L,"kolelo")
    );

    @GetMapping(value = "/packages", produces = "application/json")
    public ResponseEntity<List<Packages>> getPackages(){
        return new ResponseEntity<>(packagesArrayList, HttpStatus.OK);
    }

}
