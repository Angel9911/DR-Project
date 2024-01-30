package com.example.demo.models.entity;

import com.example.demo.models.annotations.Firstname;
import com.example.demo.models.annotations.Lastname;
import com.example.demo.models.annotations.Phone;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

@Component
@Entity
@Table(name = "couriers")
public class Courier implements Comparable<Courier>{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COURIER_SEQ")
    @SequenceGenerator(sequenceName = "couriers_info", allocationSize = 1, name = "COURIER_SEQ")
    @Column(name = "courier_id")
    private Long courierId;

    @Firstname
    @Column(name = "courier_first_name", nullable = false)
    private String courier_first_name;

    @Lastname
    @Column(name = "courier_last_name", nullable = false)
    private String courier_last_name;

    @Column(name = "courier_city_name", nullable = false)
    private String courier_city_name;
    @Phone
    @Column(name = "courier_phone", nullable = false)
    private String phone;
    @OneToMany(mappedBy = "courier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Packages> packagesList;
    @OneToOne(cascade = CascadeType.ALL) // for relationship users_acc -> users_info (one to one)
    @JoinColumn(name = "courier_acc_id",referencedColumnName="user_account_id"/*, insertable=false, updatable=false*/)
    private User_account account;

    public Courier() {
    }

    public Courier(Long courier_id, @NotBlank(message = "Name must not be empty") @Size(min = 2, max = 15) String courier_first_name, @NotBlank(message = "Last name must not be empty") @Size(min = 2, max = 15) String courier_last_name, String courier_city_name, @NotBlank(message = "Phone must not be empty") @Size(min = 10, max = 10) @Pattern(regexp = "08[789]\\d{7}", message = "Phone does not match the template.") String phone, List<Packages> packagesList, User_account account) {
        this.courierId = courier_id;
        this.courier_first_name = courier_first_name;
        this.courier_last_name = courier_last_name;
        this.courier_city_name = courier_city_name;
        this.phone = phone;
        this.packagesList = packagesList;
        this.account = account;
    }


    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courier_id) {
        this.courierId = courier_id;
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
        return Collections.unmodifiableList(packagesList);
    }

    public void setPackagesList(List<Packages> packagesList) {
        this.packagesList = packagesList;
    }

    public User_account getAccount() {
        return account;
    }

    public void setAccount(User_account user_account_courier) {
        this.account = user_account_courier;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String courier_phone) {
        this.phone = courier_phone;
    }

    public String getCourier_city_name() {
        return courier_city_name;
    }

    public void setCourier_city_name(String courier_city_name) {
        this.courier_city_name = courier_city_name;
    }

    @Override
    public int compareTo(Courier o) {
        int compareIds = this.getCourierId().compareTo(o.getCourierId());

        if(compareIds == 0){
            int compareNames = this.getCourier_first_name().compareTo(o.getCourier_first_name());

            if(compareNames == 0){

                return this.getCourier_last_name().compareTo(o.getCourier_last_name());
            }
            return compareNames;
        }

        return compareIds;
    }
}
