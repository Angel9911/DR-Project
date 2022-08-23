package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users_info")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ")
    @SequenceGenerator(sequenceName = "users_info_id", allocationSize = 1, name = "CUST_SEQ")
    private Long user_id;
    @NotBlank(message = "Name must not be empty")
    @Size(min = 2, max = 20)
    @Column(name = "name", nullable = false)
    private String name;
    @NotBlank(message = "Last name must not be empty")
    @Size(min = 2, max = 20)
    @Column(name = "last_name", nullable = false)
    private String last_name;
    @Column(name = "city", nullable = false)
    @NotBlank(message = "City must not be empty")
    private String city;
    @NotBlank(message = "Email must not be empty")
    @Size(min = 10, max = 50)
    @Pattern(regexp = "^(.+)@(.+)$", message = "Email does not match the template.")
    @Column(name = "email")
    private String email;
    @Column(name = "address", nullable = false)
    @NotBlank(message = "Address must not be empty")
    @Size(min = 10, max = 50)
    private String address;
    @NotBlank(message = "Phone must not be empty")
    @Size(min = 10, max = 10)
    @Pattern(regexp = "08[789]\\d{7}", message = "Phone does not match the template.")
    @Column(name = "phone", nullable = false)
    private String phone;
    @OneToOne(cascade = CascadeType.MERGE) // for relationship users_acc -> users_info (one to one)
    @JoinColumn(name = "user_acc_id"/*,referencedColumnName="user_account_id", insertable=false, updatable=false*/)
    private User_account user_account_customer;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<Packages> packagesList;

    public Customer(Long user_id, String name, String last_name, String city, String email, String address, String phone) {
        this.user_id = user_id;
        this.name = name;
        this.last_name = last_name;
        this.city = city;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public Customer() {

    }

    public Customer(String name, String last_name, String city, String email, String address, String phone) {
        this.name = name;
        this.last_name = last_name;
        this.city = city;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public User_account getUser_account() {
        return user_account_customer;
    }

    public void setUser_account(User_account user_account) {
        this.user_account_customer = user_account;
    }

    public List<Packages> getPackagesList() {
        return packagesList;
    }

    public void setPackagesList(List<Packages> packagesList) {
        this.packagesList = packagesList;
    }
}
