package com.example.demo.payload;

import com.example.demo.model.Customer;

import java.util.Set;

public class SignupRequest {

        private String username;

        private Set<String> roles;

        private String password;

        private Customer customer;

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

        public Set<String> getRoles() {
            return this.roles;
        }

        public void setRole(Set<String> roles) {
            this.roles = roles;
        }
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
