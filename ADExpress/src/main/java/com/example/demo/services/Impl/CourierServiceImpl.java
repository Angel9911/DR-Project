package com.example.demo.services.Impl;

import com.example.demo.models.*;
import com.example.demo.private_lib.PackageHandler;
import com.example.demo.private_lib.User;
import com.example.demo.repositories.CourierRepository;
import com.example.demo.repositories.PackageProblemRepository;
import com.example.demo.repositories.PackageRepository;
import com.example.demo.repositories.StatusRepository;
import com.example.demo.services.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.util.*;

@Service
public class CourierServiceImpl extends User implements CourierService {
    @Autowired
    CourierRepository courierRepository;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    PackageProblemRepository packageProblemRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public List<Courier> getAllCouriers() {
        return courierRepository.findAll();
    }

    @Transactional
    @Override
    public void updatePackageByStatus(int packageId, String status) throws ValidationException {
        int statusId = statusRepository.findStatusIdByStatusType(status);
        if (packageId != 0 && statusId != 0) {
            System.out.println(packageId + "" + statusId);
            LocalDate getCurrentDate = java.time.LocalDate.now();
            java.sql.Date getDate = java.sql.Date.valueOf(getCurrentDate);
            packageRepository.updateStatusPackage(statusId, packageId, getDate);
        } else {
            throw new ValidationException("error");
        }
    }

    @Transactional
    @Override
    public void updateProblemPackage(int packageId, String status, String message_problem) throws ValidationException {
        int statusId = statusRepository.findStatusIdByStatusType(status);
        if (packageId != 0 && statusId != 0) {
            System.out.println(packageId + "" + statusId);
            packageRepository.updateStatusPackageProblem(statusId, packageId);
            PackageProblem packageProblem = new PackageProblem();
            System.out.println(packageRepository.getPackageByPackageId(packageId));
            packageProblem.setPackages_problem(packageRepository.getPackageByPackageId(packageId));
            packageProblem.setMessage(message_problem);
            packageProblemRepository.save(packageProblem);
        } else {
            throw new ValidationException("error");
        }
    }

    @Transactional
    @Override
    public List<Packages> getCourierPackages(String username) throws Exception {
        if (!username.isEmpty()) {
           // List<Packages> getPackages = packageRepository.findCourierPackagesByUsername(username);
            List<Packages> getPackages2 = this.buildWhereClause(username,4);
            return PackageHandler.getPackageList(getPackages2);
        } else {
            throw new Exception("error");
        }
    }
    @Transactional
    @Override
    public List<PackageProblem> getCourierProblemPackages(String username) throws Exception {
        if (!username.isEmpty()) {
            List<PackageProblem> getPackages = packageProblemRepository.findProblemCourierPackagesByUsername(username);
            if(!getPackages.isEmpty()){
                List<Object> objectList = new ArrayList<Object>(getPackages);
                return PackageHandler.getCourierProblemPackages(objectList);
            }else {
                throw new Exception("error");
            }
        } else {
            throw new Exception("error");
        }
    }
    @Transactional
    @Override
    public List<Packages> getDeliveredPackages(String username) throws Exception {
        if (!username.isEmpty()) {
            //List<Packages> getPackages = packageRepository.findDeliveredCourierPackagesByUsername(username);
            List<Packages> getPackages2 = this.buildWhereClause(username,1);
            return PackageHandler.getPackageList(getPackages2);
        } else {
            throw new Exception("error");
        }
    }

    @Transactional
    @Override
    public Courier Login(String username) throws ValidationException {
        Courier courier = courierRepository.findCourierByUsernameAndPassword(username);
        if (courier != null) {
            Courier res = new Courier();
            res.setCourier_first_name(courier.getCourier_first_name());
            res.setCourier_last_name(courier.getCourier_last_name());
            return res;
        } else {
            throw new ValidationException("error");
        }
    }

    @Override
    public Courier Update(Object object) {
        return null;
    }

    @Override
    public void Insert(Object object) {

    }

    private List<Packages> buildWhereClause(String username, int status_id){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Packages> criteriaQuery = criteriaBuilder.createQuery(Packages.class);

        Root<Packages>packagesRoot = criteriaQuery.from(Packages.class);
        Join<Packages,TypePackage> typePackageJoin = packagesRoot.join("typePackage", JoinType.INNER);
        Join<Packages,StatusPackage> statusPackageJoin = packagesRoot.join("statusPackage", JoinType.INNER);
        Join<Packages,Customer> customerJoin = packagesRoot.join("customer", JoinType.INNER);
        Join<Packages,Courier> courierJoin = packagesRoot.join("courier", JoinType.INNER);
        Join<Courier,User_account> userAccountJoin = courierJoin.join("user_account_courier", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        if(username != null){
            predicates.add(criteriaBuilder.equal(userAccountJoin.get("username"),username));
            predicates.add(criteriaBuilder.equal(statusPackageJoin.get("status_id"),status_id));
        }

        TypedQuery<Packages> typedQuery = entityManager.createQuery(
                criteriaQuery.select(packagesRoot)
                .where(predicates.toArray(new Predicate[] {}))
        );
        return typedQuery.getResultList();
    }
}
