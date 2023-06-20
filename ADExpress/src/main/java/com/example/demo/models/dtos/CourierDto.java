package com.example.demo.models.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CourierDto {
    @NotBlank(message = "courier's name must not be empty")
    @Size(min = 2, max = 15)
    private String firstName;
    @NotBlank(message = "courier's last name must not be empty")
    @Size(min = 2, max = 15)
    private String lastNmae;

    public CourierDto() {
    }
}
