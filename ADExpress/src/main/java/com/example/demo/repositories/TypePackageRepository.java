package com.example.demo.repositories;

import com.example.demo.models.entity.TypePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypePackageRepository extends JpaRepository<TypePackage,Long> {

    Optional<TypePackage> findTypePackageByTypeId(Long typeId);

    @Query(value="SELECT t.type_id,t.type_name " +
            "FROM type_package t;",nativeQuery = true)
    List<TypePackage> findAllTypes();
    @Query(value = "SELECT t.type_id " +
            "FROM type_package t " +
            "WHERE t.type_name=:typeName",nativeQuery = true)
    Long findTypeIdByName(String typeName);

}
