package com.example.demo.models.comparators;

import com.example.demo.models.entity.Packages;

import java.util.Comparator;

public class PackageDateComparator implements Comparator<Packages> {

    @Override
    public int compare(Packages o1, Packages o2) {

        return o1.getDate_register_package().compareTo(o2.getDate_register_package());
    }
}
