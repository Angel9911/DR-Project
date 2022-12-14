package com.example.demo.repository;

import com.example.demo.model.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepository  extends CrudRepository<City,Long> {
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
