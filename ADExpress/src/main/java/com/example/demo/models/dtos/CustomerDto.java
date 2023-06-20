package com.example.demo.models.dtos;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerDto {

    @NotBlank(message = "customer's name must not be empty")
    @Size(min = 2, max = 20)
    private String name;
    @NotBlank(message = "customer's last name must not be empty")
    @Size(min = 2, max= 20)
    private String lastName;
    @NotBlank(message = "customer's email must not be empty")
    @Size(min = 10, max = 50)
    @Pattern(regexp = "^(.+)@(.+)$", message = "Email does not match the template.")
    private String email;
    @Size(min = 10, max = 10)
    @Pattern(regexp = "08[789]\\d{7}", message = "Phone does not match the template.")
    private String phone;
    @NotBlank(message = "Address must not be empty")
    @Size(min = 10, max = 50)
    private String address;

    public CustomerDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
