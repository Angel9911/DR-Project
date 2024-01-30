package com.example.demo.models.comparators;

import com.example.demo.models.entity.Packages;

import java.util.Comparator;

public class PackageIDNumberComparator implements Comparator<Packages> {
    @Override
    public int compare(Packages o1, Packages o2) {

        int compareIds = o1.getPackageId().compareTo(o2.getPackageId());

        if(compareIds == 0){
            return o1.getPackageName().compareTo(o2.getPackageName());// if the ids of two shipments are equal, then compare
            // them by name of shipment
        }

        return compareIds;
    }
}
