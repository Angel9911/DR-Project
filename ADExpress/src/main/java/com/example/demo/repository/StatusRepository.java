package com.example.demo.repository;

import com.example.demo.model.Courier;
import com.example.demo.model.StatusPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusPackage,Integer> {
    @Query(value="SELECT s.status_id FROM package_status s WHERE s.status_type=:status",nativeQuery = true)
    int findStatusIdByStatusType(String status);

}
