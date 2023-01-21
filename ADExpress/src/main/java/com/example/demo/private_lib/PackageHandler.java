package com.example.demo.private_lib;

import com.example.demo.models.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class PackageHandler {
    static List<Packages> resultPackages;
    static List<PackageProblem> packageProblemList;

    public PackageHandler() {
        resultPackages = new ArrayList<>();
    }
    public static List<PackageProblem> getCourierProblemPackages(List<Object> getPackages)throws Exception {
        if (getPackages instanceof PackageProblem) {
            List<PackageProblem> listPackageProblem = getPackages.stream()
                    .filter(element -> element instanceof PackageProblem)
                    .map(element -> (PackageProblem) element)
                    .collect(Collectors.toList());
        packageProblemList = new ArrayList<>();
        for (PackageProblem packageProblem : listPackageProblem) {
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
            Date registerDate = getDate(packageProblem.getPackages_problem().getDate_register_package());
            packages.setDate_register_package(registerDate);
            getPackageProblem.setPackages_problem(packages);
            getPackageProblem.setMessage(packageProblem.getMessage());

            packageProblemList.add(getPackageProblem);
        }
    }
        return packageProblemList;
    }
    public static List<Packages> getPackageList(List<Packages> getPackages){
        resultPackages = new ArrayList<>();
        for (Packages packages : getPackages) {
            Packages getPackage = new Packages();
            getPackage.setPackage_id(packages.getPackage_id());
            getPackage.setName_package(packages.getName_package());
            StatusPackage statusPackage = new StatusPackage();
            statusPackage.setStatus_type(packages.getStatusPackage().getStatus_type());
            TypePackage typePackage = new TypePackage();
            typePackage.setType_name(packages.getTypePackage().getType_name());
            getPackage.setStatusPackage(statusPackage);
            getPackage.setTypePackage(typePackage);
            getPackage.setPackage_price(packages.getPackage_price());
            getPackage.setTotal_cost(packages.getTotal_cost());
            Customer customer = new Customer();
            customer.setName(packages.getCustomer().getName());
            customer.setLast_name(packages.getCustomer().getLast_name());
            customer.setPhone(packages.getCustomer().getPhone());
            customer.setAddress(packages.getCustomer().getAddress());
            getPackage.setCustomer(customer); //  moje bi trqbva da se promeni na receiver
            if(packages.getDate_register_package()!=null){
                Date registerDate = getDate(packages.getDate_register_package());
                getPackage.setDate_register_package(registerDate);
            }
            if(packages.getDate_delivery_package()!=null){
                Date deliveryDate = getDate(packages.getDate_delivery_package());
                getPackage.setDate_delivery_package(deliveryDate);
            }
            getPackage.setSize_height(0);
            getPackage.setSize_width(0);
            getPackage.setReview_package(false);
            resultPackages.add(getPackage);
        }
        return resultPackages;
    }
    private static Date getDate(Date datePackage) {
        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String packageDate = outputFormatter.format(datePackage); // Output : 01/20/2012
        System.out.println(packageDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate localdate = LocalDate.parse(packageDate, formatter);
        return java.sql.Date.valueOf(localdate);
    }
}
