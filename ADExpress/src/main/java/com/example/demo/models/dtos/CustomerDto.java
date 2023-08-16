package com.example.demo.models.dtos;

import com.example.demo.models.annotations.Email;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerDto extends UserBaseDto{

    @Email
    private String email;
    @NotBlank(message = "Address must not be empty")
    @Size(min = 10, max = 50)
    private String address;

    public CustomerDto() {
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
