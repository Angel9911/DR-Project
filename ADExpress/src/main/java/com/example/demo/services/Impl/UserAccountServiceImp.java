package com.example.demo.services.Impl;

import com.example.demo.model.Customer;
import com.example.demo.model.User_account;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAccountServiceImp implements UserDetailsService {
     Logger logger = LoggerFactory.getLogger(UserAccountServiceImp.class);
    @Autowired
     UserAccountRepository userAccountRepository;
    @Autowired
     CustomerRepository customerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User_account user_account = userAccountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        logger.debug("test"+user_account.getUsername());
        logger.info(user_account.getUsername());
        return UserAccountImpl.build(user_account);
    }
}
