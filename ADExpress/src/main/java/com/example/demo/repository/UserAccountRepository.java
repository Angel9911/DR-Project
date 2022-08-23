package com.example.demo.repository;

import com.example.demo.model.User_account;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<User_account,Integer> {
    User_account findUser_accountByUsernameAndPassword(String username,String password);
    @Query(value="select a.user_account_id,a.username,a.password from user_account a where a.username=:username",nativeQuery = true)
    User_account isUsernameExist(String username);
    @Query(value="select a.user_account_id,a.username,a.password from user_account a where a.username=:username and a.password=:password",nativeQuery = true)
    User_account isPasswordExist(String username, String password);
    Optional<User_account> findByUsername(String Username);
    /* @Modifying
    @Query(value="DELETE FROM users_acc u WHERE u.username=:username",nativeQuery = true)
    Integer deleteByUsername(String username);*/
   // User_account UpdatePasswordByCustomerEmail(String username);
}
