package com.example.demo.repositories;

import com.example.demo.models.StatusPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusPackage,Integer> {
    @Query(value="SELECT s.status_id FROM package_status s WHERE s.status_type=:status",nativeQuery = true)
    int findStatusIdByStatusType(String status);

}
