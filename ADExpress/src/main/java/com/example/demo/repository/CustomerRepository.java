package com.example.demo.repository;

import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor {
    @Override
    List<Customer> findAll();

    @Query(value = "SELECT uf.user_id,uf.name,uf.last_name,uf.city,uf.email,uf.address,uf.phone,uf.user_acc_id " +
            "FROM users_info uf " +
            "INNER JOIN user_account u on uf.user_acc_id = u.user_account_id " +
            "WHERE u.username=:username", nativeQuery = true)
    Customer findUserByUsernameAndPassword(String username);

    @Query(value = "SELECT uf.user_id,uf.name,uf.last_name,uf.city,uf.email,uf.address,uf.phone,uf.user_acc_id " +
            "FROM users_info uf " +
            "INNER JOIN user_account u on uf.user_acc_id = u.user_account_id " +
            "WHERE uf.email=:email", nativeQuery = true)
    Customer findByEmail(String email);

    @Modifying
    @Query(value = "DELETE FROM users_info WHERE users_info.user_id=:user_id", nativeQuery = true)
    int deleteByUsername(int user_id);

    @Query(value = "SELECT uf.user_id " +
            "FROM users_info uf " +
            "WHERE uf.name=:name AND uf.last_name=:lastName AND uf.phone=:phone", nativeQuery = true)
    long findUserIdByUserInfo(@Param("name") String name, @Param("lastName") String lastName, @Param("phone") String phone);

    @Query(value = "SELECT c.phone FROM users_info c WHERE c.phone = :phone", nativeQuery = true)
    String IsThereExistingUser(@Param("phone") String phone);

    @Query(value = "SELECT c.user_id FROM users_info c WHERE c.phone = :phone", nativeQuery = true)
    long findUserIdByPhone(@Param("phone") String phone);

    @Modifying
    @Query(value = "UPDATE users_info SET user_acc_id = :user_acc_id, email = :email WHERE phone = :phone ", nativeQuery = true)
    void updateCustomerAccountId(@Param("user_acc_id") Long user_acc_id, @Param("email") String email, @Param("phone") String phone);
    // Customer updateUsers_infoByUsername(String username);
    /* Customer UpdateCustomerAddressByUser_accountUsername(String username);
    Customer UpdateCustomerPhoneByUser_accountUsername(String username);*/

}
