package com.example.demo.repositories;

import com.example.demo.models.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Integer> {
   @Query(value="select r.role_id,r.role_description from roles r where r.role_description=:role_description",nativeQuery = true)
   Roles findRole_idByRole_description(String role_description);
  // Optional<Roles> findByRole_description(Role name);
}
