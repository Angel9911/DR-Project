package com.example.demo.models.dtos;

import java.util.List;

public class CustomerPackagesDto {

   private List<PackageDto> listPackages;

    public CustomerPackagesDto() {
    }

    public List<PackageDto> getListPackages() {
        return listPackages;
    }

    public void setListPackages(List<PackageDto> listPackages) {
        this.listPackages = listPackages;
    }
}
