package com.example.demo.private_lib;

import com.example.demo.models.entity.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class PackageHandler {
    static List<Packages> resultPackages;
    static List<PackageProblem> packageProblemList;

    private PackageHandler() {
        resultPackages = new ArrayList<>();
    }
    public static List<PackageProblem> getCourierProblemPackages(List<PackageProblem> getPackages)throws Exception {
       /* if (getPackages instanceof PackageProblem) {
            List<PackageProblem> listPackageProblem = getPackages.stream()
                    .filter(element -> element instanceof PackageProblem)
                    .map(element -> (PackageProblem) element)
                    .collect(Collectors.toList());*/
        packageProblemList = new ArrayList<>();
        for (PackageProblem packageProblem : getPackages) {
            PackageProblem getPackageProblem = new PackageProblem();

            Packages shipmentInformation = getShipmentInformation(packageProblem.getPackages_problem());


            Customer customer = getCustomer(packageProblem.getPackages_problem());
            shipmentInformation.setCustomer(customer);

            getPackageProblem.setPackages_problem(shipmentInformation);

            getPackageProblem.setMessage(packageProblem.getMessage());

            packageProblemList.add(getPackageProblem);
        }
    //}
        return packageProblemList;
    }

    private static Packages getShipmentInformation(Packages shipment) {
        Packages shipmentData = new Packages();

        shipmentData.setPackageId(shipment.getPackageId());
        shipmentData.setName_package(shipment.getName_package());
        System.out.println(shipment.getSize_height());
        if(shipment.getSize_height() != null){
            shipmentData.setSize_height(shipment.getSize_height().intValue());
        }
        if(shipment.getSize_width() != null){
            shipmentData.setSize_width(shipment.getSize_width().intValue());
        }

        shipmentData.setReview_package(false);

        if(shipment.getPackage_price() != null){

            shipmentData.setPackage_price(shipment.getPackage_price());
        }

        if(shipment.getTotal_cost() != null){

            shipmentData.setTotal_cost(shipment.getTotal_cost());
        }

        if(shipment.getDate_register_package()!=null){
            Date registerDate = getDate(shipment.getDate_register_package());
            shipmentData.setDate_register_package(registerDate);
        }

        if(shipment.getDate_delivery_package()!=null){
            Date deliveryDate = getDate(shipment.getDate_delivery_package());
            shipmentData.setDate_delivery_package(deliveryDate);
        }

        StatusPackage statusPackage = getStatusPackage(shipment);
        shipment.setStatusPackage(statusPackage);

        TypePackage typePackage = getTypePackage(shipment);
        shipment.setTypePackage(typePackage);

        return shipmentData;
    }

    public static List<Packages> getPackageList(List<Packages> getPackages){
        resultPackages = new ArrayList<>();
        for (Packages packages : getPackages) {

            Packages getPackage = getShipmentInformation(packages);

            Customer customer = getCustomer(packages);
            getPackage.setCustomer(customer); //  moje bi trqbva da se promeni na receiver

            resultPackages.add(getPackage);
        }
        return resultPackages;
    }

    private static StatusPackage getStatusPackage(Packages shipment) {
        StatusPackage statusPackage = new StatusPackage();
        statusPackage.setStatus_type(shipment.getStatusPackage().getStatus_type());
        return statusPackage;
    }

    private static TypePackage getTypePackage(Packages shipment) {
        TypePackage typePackage = new TypePackage();
        typePackage.setType_name(shipment.getTypePackage().getType_name());
        return typePackage;
    }

    private static Customer getCustomer(Packages shipment) {
        Customer customer = new Customer();
        customer.setName(shipment.getCustomer().getName());
        customer.setLastName(shipment.getCustomer().getLastName());
        customer.setAddress(shipment.getCustomer().getAddress());
        customer.setPhone(shipment.getCustomer().getPhone());
        return customer;
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
