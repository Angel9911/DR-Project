package com.example.demo.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "user_account")
public class User_account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(sequenceName = "user_account_id", allocationSize = 1, name = "USER_SEQ")
    private Long user_account_id;
    @NotBlank(message = "Username must not be empty")
    @Size(min = 2, max = 20)
    @Column(name = "username", nullable = false)
    private String username;
    @NotBlank(message = "Password must not be empty")
    @Size(min = 2, max = 100)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password does not match the template.")
    @Column(name = "password", nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_account_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Roles> userRoles;
    @OneToOne(mappedBy = "user_account_courier"/*,fetch = FetchType.LAZY,cascade = CascadeType.ALL*/)
    @JsonIgnore
    private Courier courier;
    @OneToOne(mappedBy = "user_account_customer"/*, fetch = FetchType.LAZY,cascade = CascadeType.ALL*/)
    @JsonIgnore
    private Customer customer;
    @OneToOne(mappedBy = "user_account_administrator"/*, fetch = FetchType.LAZY,cascade = CascadeType.ALL*/)
    @JsonIgnore
    private Administrator administrator;

    public User_account(Long user_account_id, String username, String password, List<Roles> roles) {
        this.user_account_id = user_account_id;
        this.username = username;
        this.password = password;
        this.userRoles = roles;
    }

    public User_account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getUser_account_id() {
        return user_account_id;
    }

    public void setUser_account_id(Long user_account_id) {
        this.user_account_id = user_account_id;
    }

    public User_account() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Roles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<Roles> userRoles) {
        this.userRoles = userRoles;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }
}
