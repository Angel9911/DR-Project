package com.example.authservice.config;

import com.example.authservice.entity.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final List<UserAccount> listUsers = new ArrayList<>();
        listUsers.add(new UserAccount("testuser1","testuser1", "ADMIN"));
        listUsers.add(new UserAccount("testuser2","testuser2", "USER"));

        for (UserAccount userAccount:listUsers) {
            if(userAccount.getUsername().equals(username)){

                List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_"+userAccount.getRole());
                return new User(userAccount.getUsername(),userAccount.getPassword(),grantedAuthorities);
            }
        }
        throw new UsernameNotFoundException("Username: " + username + " not found");
    }
}
