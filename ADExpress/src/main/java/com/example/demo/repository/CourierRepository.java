package com.example.demo.repository;

import com.example.demo.model.Courier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CourierRepository extends CrudRepository<Courier, Integer> {
    List<Courier> findAll();

    @Query(value = "SELECT c.courier_first_name,c.courier_last_name,c.courier_acc_id,c.courier_city_name, c.courier_phone,c.courier_id " +
            "FROM couriers c " +
            "INNER JOIN user_account u on c.courier_acc_id = u.user_account_id " +
            "WHERE u.username=:username", nativeQuery = true)
    Courier findCourierByUsernameAndPassword(@Param("username") String username);

    @Query(value = "DELETE users_acc, couriers FROM users_acc JOIN couriers WHERE users_acc.username=:username  AND couriers.courier_phone=:phone ", nativeQuery = true)
    Integer deleteByUsernameAndPhone(String username, String phone);

    @Query(value = "SELECT c.courier_phone, COUNT(p.package_id) as packagesCount " +
            "FROM couriers c " +
            "INNER JOIN packages p on p.courier_id = c.courier_id " +
            "INNER JOIN offices o on p.office_id = o.office_id " +
            "WHERE c.courier_city_name=:cityName " +
            "group by " +
            "    c.courier_first_name, " +
            "    c.courier_phone " +
            "order by packagesCount asc;", nativeQuery = true)
    Map<Courier, Integer> findPackagesByCityName(@Param("cityName") String cityName);

    @Query(value = "SELECT c.courier_id,c.courier_first_name,c.courier_last_name,c.courier_phone,c.courier_acc_id, COUNT(p.package_id) as packagesCount " +
            "FROM couriers c " +
            "INNER JOIN packages p on p.courier_id = c.courier_id " +
            "INNER JOIN offices o on p.office_id = o.office_id " +
            "WHERE c.courier_city_name=:cityName " +
            "group by " +
            "    c.courier_id, " +
            "    c.courier_first_name, " +
            "    c.courier_last_name, " +
            "    c.courier_phone, " +
            "    c.courier_acc_id " +
            "order by packagesCount asc;", nativeQuery = true)
    List<Courier> findPackagesByCityName1(@Param("cityName") String cityName);
}
