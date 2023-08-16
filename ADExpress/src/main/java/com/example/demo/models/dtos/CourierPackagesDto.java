package com.example.demo.models.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class CourierPackagesDto {
    @NotBlank(message = "Name must not be empty")
    @Size(min = 2, max = 15)
    private String courier_first_name;

    @NotBlank(message="Last name must not be empty")
    @Size(min=2, max=15)
    private String courier_last_name;

    private List<PackageDto> packages;
}
