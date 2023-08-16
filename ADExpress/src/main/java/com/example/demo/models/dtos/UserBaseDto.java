package com.example.demo.models.dtos;

import com.example.demo.models.annotations.Firstname;
import com.example.demo.models.annotations.Lastname;
import com.example.demo.models.annotations.Phone;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserBaseDto {

    @Firstname
    private String firstName;

    @Lastname
    private String lastName;

    @Phone
    @Pattern(regexp = "08[789]\\d{7}", message = "Phone does not match the template.")
    private String phone;

    @NotBlank(message = "City must not be empty")
    private String city;

    public UserBaseDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
