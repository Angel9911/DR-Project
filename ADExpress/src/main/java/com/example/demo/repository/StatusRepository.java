package com.example.demo.repository;

import com.example.demo.model.Courier;
import com.example.demo.model.StatusPackage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StatusRepository extends CrudRepository<StatusPackage,Integer> {
    @Query(value="SELECT s.status_id FROM package_status s WHERE s.status_type=:status",nativeQuery = true)
    int findStatusIdByStatusType(String status);

}
