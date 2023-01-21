package com.example.demo.repositories;

import com.example.demo.models.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator,Long> {
    @Query(value="SELECT u.user_account_id, a.administrator_id, a.administrator_acc_id, o.office_id, c.city_id, u.username, u.password, c.city_name, o.office_location "+
            "FROM user_account u "+
            "INNER JOIN user_roles ur on ur.user_account_id = u.user_account_id " +
            "INNER JOIN administrator_office a on a.administrator_acc_id = u.user_account_id " +
            "INNER JOIN offices o on a.office_id = o.office_id " +
            "INNER JOIN cities c on o.city_id = c.city_id "+
            "WHERE u.username=:username", nativeQuery = true)
    Administrator findAdministratorByUsername(@Param("username") String username);
  /*  @Query("select c.name,c.last_name,c.phone,p.name_package,t.type_name,s.status_type " +
            "from customer c" +
            "inner join packages p on p.customer_id = c.customer_id" +
            "inner join type_package t on p.type_package_id = t.type_id" +
            "inner join package_status s on p.status_id = s.status_id" +
            "where c.phone = :#{#customer.phone}")
    List<Packages> findPackagesByCustomerPhone(@Param("customer") Customer customer);

    int findType_idByTypeName(String type_name);
    int findStatus_idByStatus_type(String type_status);
    int findCourier_idByCourier_phone(String courier_phone);
    int findCustomer_idByPhone(String customer_phone);

    Packages savePackages(String name_package, Long status_id, Long type_package_id, Long office_id, Long courier_id);*/

}
