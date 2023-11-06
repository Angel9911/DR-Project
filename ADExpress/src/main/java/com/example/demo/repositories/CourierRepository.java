package com.example.demo.repositories;

import com.example.demo.models.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
    List<Courier> findAll();

    Optional<Courier> findByAccount_Username(String username);

    @Query(value = "SELECT c.courier_id " +
            "FROM couriers c " +
            "WHERE c.courier_id = :id",nativeQuery = true)
    Optional<Integer> findCourierById(@Param("id") int id);

    int deleteByPhoneAndAccount_Username(String phone,String username);


    @Query(value = "SELECT c.courier_id,c.courier_first_name,c.courier_last_name,c.courier_phone,c.courier_acc_id,c.courier_city_name, COUNT(p.package_id) as packagesCount " +
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
    List<Courier> findPackagesByCityName(@Param("cityName") String cityName);



    /* @Query(value = "SELECT c.courier_first_name,c.courier_last_name,c.courier_acc_id,c.courier_city_name, c.courier_phone,c.courier_id " +
            "FROM couriers c " +
            "INNER JOIN user_account u on c.courier_acc_id = u.user_account_id " +
            "WHERE u.username=:username", nativeQuery = true)
    Courier findCourierByUsernameAndPassword(@Param("username") String username);

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
    */
}
