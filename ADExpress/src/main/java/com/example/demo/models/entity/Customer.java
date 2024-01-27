package com.example.demo.models.entity;
import com.example.demo.models.annotations.Email;
import com.example.demo.models.annotations.Firstname;
import com.example.demo.models.annotations.Lastname;
import com.example.demo.models.annotations.Phone;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users_info")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ")
    @SequenceGenerator(sequenceName = "users_info_id", allocationSize = 1, name = "CUST_SEQ")
    @Column(name = "user_id")
    private Long userId;

    @Firstname
    @Column(name = "name", nullable = false)
    private String name;

    @Lastname
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "city", nullable = false)
    @NotBlank(message = "City must not be empty")
    private String city;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "address", nullable = false)
    @NotBlank(message = "Address must not be empty")
    @Size(min = 10, max = 50)
    private String address;

    @Phone
    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToOne(cascade = CascadeType.ALL) // for relationship users_acc -> users_info (one to one)
    @JoinColumn(name = "user_acc_id",referencedColumnName="user_account_id"/*, insertable=false, updatable=false*/)
    private User_account account;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<Packages> packagesList;

    public Customer(Long user_id, String name, String lastName, String city, String email, String address, String phone) {
        this.userId = user_id;
        this.name = name;
        this.lastName = lastName;
        this.city = city;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public Customer() {

    }

    public Customer(String name, String lastName, String city, String email, String address, String phone) {
        this.name = name;
        this.lastName = lastName;
        this.city = city;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user_id) {
        this.userId = user_id;
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

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User_account getAccount() {
        return account;
    }

    public void setAccount(User_account user_account) {
        this.account = user_account;
    }

    public List<Packages> getPackagesList() {
        return Collections.unmodifiableList(packagesList);
    }

    public void setPackagesList(List<Packages> packagesList) {
        this.packagesList = packagesList;
    }
}
