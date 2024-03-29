package com.example.demo.repositories;

import com.example.demo.models.entity.StatusPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<StatusPackage,Integer> {

    @Query(value="SELECT s.status_id FROM package_status s WHERE s.status_type=:status",nativeQuery = true)
    int findStatusIdByStatusType(String status);

    Optional<StatusPackage> findStatusPackageByStatusId(Long statusId);
}
