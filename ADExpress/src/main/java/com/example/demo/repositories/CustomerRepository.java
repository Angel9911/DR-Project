package com.example.demo.repositories;

import com.example.demo.models.entity.Customer;
import com.example.demo.models.views.CustomerView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor {

    @Override
    List<Customer> findAll();

    //Customer findByAccount_Username(String username);
    Optional<Customer> findById(Long id);

    CustomerView findByAccount_Username(String username);

    Optional<Customer> findByEmail(String email);

    Customer findCustomerByPhone(String phone);

    CustomerView findByUserId(Long id);

    @Modifying
    @Query(value = "DELETE FROM users_info WHERE users_info.user_id=:user_id", nativeQuery = true)
    int deleteByUserId(int user_id);

    @Query(value = "SELECT uf.user_id " +
            "FROM users_info uf " +
            "WHERE uf.name=:name AND uf.last_name=:lastName AND uf.phone=:phone", nativeQuery = true)
    long findUserIdByUserInfo(@Param("name") String name, @Param("lastName") String lastName, @Param("phone") String phone);

    @Query(value = "SELECT uf.user_id " +
            "FROM users_info uf " +
            "WHERE uf.user_id = :id", nativeQuery = true)
    Optional<Integer> findByUser_id(@Param("id") int id);

    @Modifying
    @Query(value = "UPDATE users_info SET user_acc_id = :user_acc_id, email = :email WHERE phone = :phone ", nativeQuery = true)
    void updateCustomerAccountId(@Param("user_acc_id") Long user_acc_id, @Param("email") String email, @Param("phone") String phone);



    // Customer updateUsers_infoByUsername(String username);
    /* useless queries Customer UpdateCustomerAddressByUser_accountUsername(String username);
    Customer UpdateCustomerPhoneByUser_accountUsername(String username);
    @Query(value = "SELECT c.phone FROM users_info c WHERE c.phone = :phone", nativeQuery = true)
    String IsThereExistingUser(@Param("phone") String phone);
     @Query(value = "SELECT uf.user_id,uf.name,uf.last_name,uf.city,uf.email,uf.address,uf.phone,uf.user_acc_id " +
            "FROM users_info uf " +
            "INNER JOIN user_account u on uf.user_acc_id = u.user_account_id " +
            "WHERE u.username=:username", nativeQuery = true)
    Customer findUserByUsernameAndPassword(String username);
    */

}
