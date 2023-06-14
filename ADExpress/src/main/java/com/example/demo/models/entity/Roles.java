package com.example.demo.models.entity;

import com.example.demo.models.enums.Role;

import javax.persistence.*;

@Entity
@Table(name="roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;
    @Enumerated(EnumType.STRING)
    @Column(name = "role_description", nullable = false)
    private Role role_description;
   /* @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_roles", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "user_account_id") })
    private List<User_account> userAccounts; */

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public Role getRole_description() {
        return role_description;
    }

    public void setRole_description(Role role_description) {
        this.role_description = role_description;
    }

   /* public List<User_account> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(List<User_account> userAccounts) {
        this.userAccounts = userAccounts;
    } */


}
