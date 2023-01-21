package com.example.demo.repository;

import com.example.demo.model.TypePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TypePackageRepository extends JpaRepository<TypePackage,Long> {
    @Query(value="SELECT t.type_id,t.type_name " +
            "FROM type_package t;",nativeQuery = true)
    List<TypePackage> findAllTypes();
    @Query(value = "SELECT t.type_id " +
            "FROM type_package t " +
            "WHERE t.type_name=:typeName",nativeQuery = true)
    Long findTypeIdByName(String typeName);

}
