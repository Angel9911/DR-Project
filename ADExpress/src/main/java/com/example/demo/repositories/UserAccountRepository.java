package com.example.demo.repositories;

import com.example.demo.models.User_account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserAccountRepository extends JpaRepository<User_account,Integer> {
    User_account findUser_accountByUsernameAndPassword(String username,String password);
    @Query(value="select a.user_account_id,a.username,a.password from user_account a where a.username=:username",nativeQuery = true)
    User_account isUsernameExist(String username);
    @Query(value="select a.user_account_id,a.username,a.password from user_account a where a.username=:username and a.password=:password",nativeQuery = true)
    User_account isPasswordExist(String username, String password);
    Optional<User_account> findByUsername(String Username);
}
