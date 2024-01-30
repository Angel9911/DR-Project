package com.example.demo.models.comparators;

import com.example.demo.models.entity.Packages;

import java.util.Comparator;

public class PackagePriceComparator implements Comparator<Packages> {
    @Override
    public int compare(Packages o1, Packages o2) {
        int comparePrice = o1.getPackage_price().compareTo(o2.getPackage_price());

        if(comparePrice == 0){
            return o1.getTotal_cost().compareTo(o2.getTotal_cost()); // if prices of two shipments are equal, then compare
            // them by total cost
        }
        return comparePrice;
    }
}
