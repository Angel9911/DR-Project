package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "couriers")
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COURIER_SEQ")
    @SequenceGenerator(sequenceName = "couriers_info", allocationSize = 1, name = "COURIER_SEQ")
    private Long courier_id;
    @NotBlank(message = "Name must not be empty")
    @Size(min = 2, max = 15)
    @Column(name = "courier_first_name", nullable = false)
    private String courier_first_name;
    @NotBlank(message="Last name must not be empty")
    @Size(min=2, max=15)
    @Column(name = "courier_last_name", nullable = false)
    private String courier_last_name;
    @Column(name = "courier_city_name", nullable = false)
    private String courier_city_name;
    @NotBlank(message="Phone must not be empty")
    @Size(min=10, max=10)
    @Pattern(regexp="08[789]\\d{7}", message="Phone does not match the template.")
    @Column(name = "courier_phone", nullable = false)
    private String courier_phone;
    @OneToMany(mappedBy = "courier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Packages> packagesList;
    @OneToOne(cascade = CascadeType.ALL) // for relationship users_acc -> users_info (one to one)
    @JoinColumn(name = "courier_acc_id"/*,referencedColumnName="user_account_id", insertable=false, updatable=false*/)
    private User_account user_account_courier;

    public Courier() {
    }

    public Courier(Long courier_id, @NotBlank(message = "Name must not be empty") @Size(min = 2, max = 15) String courier_first_name, @NotBlank(message = "Last name must not be empty") @Size(min = 2, max = 15) String courier_last_name, String courier_city_name, @NotBlank(message = "Phone must not be empty") @Size(min = 10, max = 10) @Pattern(regexp = "08[789]\\d{7}", message = "Phone does not match the template.") String courier_phone, List<Packages> packagesList, User_account user_account_courier) {
        this.courier_id = courier_id;
        this.courier_first_name = courier_first_name;
        this.courier_last_name = courier_last_name;
        this.courier_city_name = courier_city_name;
        this.courier_phone = courier_phone;
        this.packagesList = packagesList;
        this.user_account_courier = user_account_courier;
    }


    public Long getCourier_id() {
        return courier_id;
    }

    public void setCourier_id(Long courier_id) {
        this.courier_id = courier_id;
    }


    public String getCourier_first_name() {
        return courier_first_name;
    }

    public void setCourier_first_name(String courier_first_name) {
        this.courier_first_name = courier_first_name;
    }

    public String getCourier_last_name() {
        return courier_last_name;
    }

    public void setCourier_last_name(String courier_last_name) {
        this.courier_last_name = courier_last_name;
    }

    public List<Packages> getPackagesList() {
        return packagesList;
    }

    public void setPackagesList(List<Packages> packagesList) {
        this.packagesList = packagesList;
    }

    public User_account getUser_account_courier() {
        return user_account_courier;
    }

    public void setUser_account_courier(User_account user_account_courier) {
        this.user_account_courier = user_account_courier;
    }

    public String getCourier_phone() {
        return courier_phone;
    }

    public void setCourier_phone(String courier_phone) {
        this.courier_phone = courier_phone;
    }

    public String getCourier_city_name() {
        return courier_city_name;
    }

    public void setCourier_city_name(String courier_city_name) {
        this.courier_city_name = courier_city_name;
    }

}
