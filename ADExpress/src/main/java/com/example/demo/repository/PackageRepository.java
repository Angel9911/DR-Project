package com.example.demo.repository;

import com.example.demo.model.Packages;
import com.example.demo.model.StatusPackage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PackageRepository  extends CrudRepository<Packages,Integer> {
    List<Packages> findAll();
    @Modifying
    @Query(value="UPDATE packages SET status_id = :status_id, date_delivery_package = :delivery_date WHERE package_id = :package_id",nativeQuery = true)
    void updateStatusPackage(int status_id, int package_id, Date delivery_date);
    @Modifying
    @Query(value="UPDATE packages SET status_id = :status_id WHERE package_id = :package_id",nativeQuery = true)
    void updateStatusPackageProblem(int status_id, int package_id);
    @Query(value = "select p.name_package, t.type_name, uf.address, s.status_type, p.package_id, p.courier_id, p.user_id, p.office_id, p.review_package, p.size_height, p.size_width, p.weight_package, p.date_register_package, p.date_delivery_package, p.package_price, p.total_cost, p.receiver_id, p.status_id, p.type_package_id, uf.name, uf.last_name, uf.phone "+
            "from packages p "+
            "inner join type_package t on p.type_package_id = t.type_id "+
         //   "inner join offices o on p.office_id = o.office_id "+
          //  "inner join cities ct on o.city_id = ct.city_id "+
            "inner join package_status s on p.status_id = s.status_id "+
            "inner join users_info uf on p.user_id = uf.user_id "+
            "inner join user_account u on uf.user_acc_id = u.user_account_id "+
            "where u.username = :username",nativeQuery = true)
    List<Packages> findPackagesByUser_accountUsername(String username);
    @Query(value = "select p.name_package, t.type_name, uf.address, s.status_type, p.package_id, p.courier_id, p.user_id, p.office_id, p.review_package, p.size_height, p.size_width, p.weight_package, p.package_price, p.date_register_package, p.date_delivery_package, p.total_cost, p.status_id, p.receiver_id, p.type_package_id, uf.name, uf.last_name, uf.phone "+
            "from packages p " +
            "inner join type_package t on p.type_package_id = t.type_id "+
            "inner join package_status s on p.status_id = s.status_id "+
            "inner join users_info uf on p.user_id = uf.user_id "+
            "inner join couriers cr on p.courier_id = cr.courier_id "+
            "inner join user_account us on cr.courier_acc_id = us.user_account_id "+
          //  "inner join user_account u on uf.user_acc_id = u.user_account_id "+
            "where us.username = :username and p.status_id=4",nativeQuery = true)
    List<Packages> findCourierPackagesByUsername(String username);
    @Query(value = "select p.name_package, t.type_name, uf.address, s.status_type, p.package_id, p.courier_id, p.user_id, p.office_id, p.review_package, p.size_height, p.size_width, p.weight_package, p.package_price, p.date_register_package, p.date_delivery_package, p.total_cost, p.status_id, p.type_package_id, p.receiver_id, uf.name, uf.last_name, uf.phone "+
            "from packages p " +
            "inner join type_package t on p.type_package_id = t.type_id "+
            "inner join package_status s on p.status_id = s.status_id "+
            "inner join users_info uf on p.user_id = uf.user_id "+
            "inner join couriers cr on p.courier_id = cr.courier_id "+
            "inner join user_account us on cr.courier_acc_id = us.user_account_id "+
           // "inner join user_account u on uf.user_acc_id = u.user_account_id "+
            "where us.username = :username and p.status_id=1",nativeQuery = true)
    List<Packages> findDeliveredCourierPackagesByUsername(String username);
    @Query(value = "select c.name,c.last_name,c.phone,p.name_package,t.type_name,s.status_type " +
            "from customer c" +
            "inner join packages p on p.customer_id = c.customer_id" +
            "inner join type_package t on p.type_package_id = t.type_id" +
            "inner join package_status s on p.status_id = s.status_id" +
            "where c.phone = :customerPhone",nativeQuery = true)
    List<Packages> findPackagesByCustomerPhone(@Param("customerPhone") String customerPhone);
    @Query(value = "select p.name_package, t.type_name, uf.address, s.status_type, p.package_id, p.courier_id, p.user_id, p.office_id, p.review_package, p.size_height, p.size_width, p.package_price, p.total_cost, p.weight_package, p.date_register_package, p.date_delivery_package, p.status_id, p.type_package_id, p.receiver_id, uf.name, uf.last_name, uf.phone "+
            "from packages p " +
            "inner join type_package t on p.type_package_id = t.type_id "+
            "inner join package_status s on p.status_id = s.status_id "+
            "inner join users_info uf on p.user_id = uf.user_id "+
            "inner join couriers cr on p.courier_id = cr.courier_id "+
            "inner join user_account us on cr.courier_acc_id = us.user_account_id "+
          //  "inner join user_account u on uf.user_acc_id = u.user_account_id "+
            "where p.package_id = :packageId ", nativeQuery = true)
    Packages getPackageByPackageId(int packageId);
}
