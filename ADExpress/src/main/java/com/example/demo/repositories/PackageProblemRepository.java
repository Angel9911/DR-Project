package com.example.demo.repositories;

import com.example.demo.models.PackageProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageProblemRepository extends JpaRepository<PackageProblem,Integer> {
    @Query(value = "select p.name_package, t.type_name, uf.address, s.status_type, pr.message, pr.package_problem_id, p.package_id, p.courier_id, p.user_id, p.office_id, p.review_package, p.size_height, p.size_width, p.weight_package, p.status_id, p.type_package_id, uf.name, uf.last_name, uf.phone "+
            "from packages p " +
            "inner join package_problems pr on pr.package_id = p.package_id " +
            "inner join type_package t on p.type_package_id = t.type_id "+
            "inner join package_status s on p.status_id = s.status_id "+
            "inner join users_info uf on p.user_id = uf.user_id "+
            "inner join couriers cr on p.courier_id = cr.courier_id "+
            "inner join user_account us on cr.courier_acc_id = us.user_account_id "+
            "where us.username = :username and p.status_id=2",nativeQuery = true)
    List<PackageProblem> findProblemCourierPackagesByUsername(String username);
}
