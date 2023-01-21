package com.example.demo.services.Impl;

import com.example.demo.models.Customer;
import com.example.demo.models.User_account;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.RolesRepository;
import com.example.demo.repositories.UserAccountRepository;
import com.example.demo.services.JwtAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class JwtAuthServiceImpl implements JwtAuthService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    UserAccountRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @Transactional
    public void isUserExisting(User_account user_account, @RequestBody Customer signupRequest) {
       /* User_account user = new User_account(signupRequest.getUsername(),
                encoder.encode(signupRequest.getPassword()));
        insertUserRoles(signupRequest, user);
        userRepository.save(user);
         this.getCustomerId = customerRepository.findUserIdByPhone(signupRequest.getCustomer().getPhone());
        Customer customer = new Customer();
        customer.setUser_id(user.getUser_account_id());
        customer.setEmail(signupRequest.getCustomer().getEmail());
        customer.setPhone(signupRequest.getCustomer().getPhone());
        Customer customer = new Customer();
        customer.setName(signupRequest.getName());
        customer.setLast_name(signupRequest.getLast_name());
        customer.setCity(signupRequest.getCity());
        customer.setAddress(signupRequest.getAddress()); */
        customerRepository.updateCustomerAccountId(user_account.getUser_account_id(), signupRequest.getEmail(), signupRequest.getPhone());
        //customerRepository.updateCustomerAccountId(user.getUser_account_id(), signupRequest.getCustomer().getEmail(), signupRequest.getCustomer().getPhone());
    }

    /*public void insertUserRoles(@RequestBody SignupRequest signupRequest, User_account user) {
        Set<String> strRoles = signupRequest.getRoles();
        for (String role :
                strRoles) {
            System.out.println(role);
        }
        List<Roles> roles = new ArrayList<>();
        strRoles.forEach(role -> {
            if ("administrator".equals(role)) {
                Roles adminRole = rolesRepository.findRole_idByRole_description(role);
                //  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
            } else if ("courier".equals(role)) {
                Roles modRole = rolesRepository.findRole_idByRole_description(role);
                //    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(modRole);
            } else {
                Roles userRole = rolesRepository.findRole_idByRole_description(role);
                //   .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }
        });
        user.setUserRoles(roles);
    } */
}
