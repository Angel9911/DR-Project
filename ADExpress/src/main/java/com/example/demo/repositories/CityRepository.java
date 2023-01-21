package com.example.demo.repositories;

import com.example.demo.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CityRepository  extends JpaRepository<City,Long> {
    List<City> findAll();
    @Query(value="select c.city_id,c.city_name from cities c where c.city_id=:city_id",nativeQuery = true)
    City findCityById(int city_id);
    @Query(value="select c.city_id,c.city_name from cities c where c.city_name=:city_name",nativeQuery = true)
    City findCityNameById(String city_name);
    @Query(value = "SELECT c.city_id " +
            "FROM cities c " +
            "WHERE c.city_name=:city_name",nativeQuery = true)
    Long findCityIdByName(String city_name);
}
