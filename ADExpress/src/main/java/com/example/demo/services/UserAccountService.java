package com.example.demo.services;

import com.example.demo.model.Customer;
import com.example.demo.model.User_account;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAccountService {
    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    private CustomerService customerService;

    @Transactional
    public User_account IsUsernameExist(String username) {
        return userAccountRepository.isUsernameExist(username);
    }

    @Transactional
    public User_account IsPasswosrdExist(String username, String password) {
        return userAccountRepository.isPasswordExist(username, password);
    }

    @Transactional
    public User_account getUser(String username, String password) {
        return (User_account) userAccountRepository.findUser_accountByUsernameAndPassword(username, password);
    }

    @Transactional
    public void insertAccount(Customer customer) {
      /*  User_account user = new User_account(customer.getUser_account().getUsername(),
                encoder.encode(customer.getUser_account().getPassword()));

        List<Roles> strRoles = customer.getUser_account().getUserRoles();
        for (Roles role:
             strRoles) {
            System.out.println(role.getRole_description().name());
        }
        List<Roles> roles = new ArrayList<>();
        strRoles.forEach(role -> {
            if ("administrator".equals(role.getRole_description().name())) {
                Roles adminRole = rolesRepository.findRole_idByRole_description(role.getRole_description().name());
                //  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
            } else if ("courier".equals(role.getRole_description().name())) {
                Roles modRole = rolesRepository.findRole_idByRole_description(role.getRole_description().name());
                //    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(modRole);
            } else {
                Roles userRole = rolesRepository.findRole_idByRole_description(role.getRole_description().name());
                System.out.println(userRole.getRole_id());
                //   .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }
        });
        user.setUserRoles(roles);
        userAccountRepository.save(user);
        Customer result = new Customer();
        result.setName(customer.getName());
        result.setLast_name(customer.getLast_name());
        City city = new City();
        city.setCity_id(customer.getCity().getCity_id());
        result.setCity(city);
        result.setEmail(customer.getEmail());
        result.setAddress(customer.getAddress());
        result.setPhone(customer.getPhone());
        User_account user_account = new User_account();
        user_account.setUser_account_id(user_account.getUser_account_id());
        result.setUser_account(user_account);
        customerService.insertCustomer(customer); */
    }
}
