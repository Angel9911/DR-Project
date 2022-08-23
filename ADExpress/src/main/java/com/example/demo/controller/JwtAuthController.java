package com.example.demo.controller;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.Customer;
import com.example.demo.model.Roles;
import com.example.demo.model.User_account;
import com.example.demo.model.jwt.JwtResponse;
import com.example.demo.payload.SignupRequest;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.services.CustomerService;
import com.example.demo.services.Impl.UserAccountImpl;
import com.example.demo.services.JwtAuthService;
import com.example.demo.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class JwtAuthController {
    @Autowired
    UserAccountRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserAccountService userAccountService;
    @Autowired
    CustomerService customerService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtAuthService authService;
    Long getCustomerId = 0L;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> userLogin(@RequestBody User_account user_account) {
        System.out.println("AuthController -- userLogin");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user_account.getUsername(), user_account.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateJwtToken(authentication);
        UserAccountImpl UserAccountImpl = (UserAccountImpl) authentication.getPrincipal();
        List<String> roles = UserAccountImpl.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toList());
        JwtResponse authResponse = new JwtResponse();
        authResponse.setToken(token);
        authResponse.setRoles(roles);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody SignupRequest signupRequest) throws Exception {
// Create new user's account
        System.out.println("test in auth controller" + signupRequest.getUsername());
        String isUserExisting = customerRepository.IsThereExistingUser(signupRequest.getCustomer().getPhone());
        System.out.println("test in auth controller2" + isUserExisting);
        if (isUserExisting != null) {
            User_account user = new User_account(signupRequest.getUsername(),
                    encoder.encode(signupRequest.getPassword()));
            insertUserRoles(signupRequest, user);
            userRepository.save(user);
            authService.isUserExisting(user,signupRequest.getCustomer());
            return new ResponseEntity<String>("User has registered successfully", HttpStatus.OK);
        } else {
            User_account user = new User_account(signupRequest.getUsername(),
                    encoder.encode(signupRequest.getPassword()));
            System.out.println(signupRequest.getRoles());
            insertUserRoles(signupRequest, user);
            Customer result = signupRequest.getCustomer();
            result.setName(signupRequest.getCustomer().getName());
            result.setLast_name(signupRequest.getCustomer().getLast_name());
            result.setCity(signupRequest.getCustomer().getCity());
            result.setEmail(signupRequest.getCustomer().getEmail());
            result.setAddress(signupRequest.getCustomer().getAddress());
            result.setPhone(signupRequest.getCustomer().getPhone());
            userRepository.save(user);
          //  User_account user_account = new User_account();
           // user_account.setUser_account_id(user.getUser_account_id());
            result.setUser_account(user);
            customerService.insertCustomer(result);
            return new ResponseEntity<String>("User has registered successfully", HttpStatus.OK);
        }
    }
    private void insertUserRoles(@RequestBody SignupRequest signupRequest, User_account user) {
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
    }
}
