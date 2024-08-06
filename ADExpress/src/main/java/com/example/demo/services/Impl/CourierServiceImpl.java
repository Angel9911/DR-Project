package com.example.demo.services.Impl;

import com.example.demo.events.NotificationMessageEvent;
import com.example.demo.models.entity.*;
import com.example.demo.models.enums.Role;
import com.example.demo.private_lib.AwsS3Client;
import com.example.demo.private_lib.PackageHandler;
import com.example.demo.private_lib.User;
import com.example.demo.repositories.CourierRepository;
import com.example.demo.repositories.PackageProblemRepository;
import com.example.demo.repositories.PackageRepository;
import com.example.demo.repositories.StatusRepository;
import com.example.demo.services.CourierService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.regions.Region;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.util.*;

@Service
@CacheConfig(cacheNames = {"courier"})
public class CourierServiceImpl extends User implements CourierService {

    private final CourierRepository courierRepository;

    private final PackageRepository packageRepository;

    private final StatusRepository statusRepository;

    private final PackageProblemRepository packageProblemRepository;

    private final ApplicationEventPublisher eventPublisher;

    @PersistenceContext
    private EntityManager entityManager;
    @Value("${aws.bucketName}")
    String bucketName;

    public CourierServiceImpl(CourierRepository courierRepository, PackageRepository packageRepository, StatusRepository statusRepository, PackageProblemRepository packageProblemRepository, ApplicationEventPublisher eventPublisher) {
        this.courierRepository = courierRepository;
        this.packageRepository = packageRepository;
        this.statusRepository = statusRepository;
        this.packageProblemRepository = packageProblemRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public List<Courier> getAllCouriers() {
        return courierRepository.findAll();
    }

    @CachePut(key="#packageId")
    @Transactional
    @Override
    public void updatePackageByStatus(int packageId, String status) throws ValidationException {
        int statusId = statusRepository.findStatusIdByStatusType(status);
        if (packageId != 0 && statusId != 0) {

            System.out.println(packageId + "" + statusId);

            if(statusId == 5){
                Customer getCustomerByPackage = this.getCustomerDataByPackage(packageId);
                eventPublisher.publishEvent(new NotificationMessageEvent(this,packageId,getCustomerByPackage.getPhone(),"test_courier_order"));
            }

            LocalDate getCurrentDate = java.time.LocalDate.now();

            java.sql.Date getDate = java.sql.Date.valueOf(getCurrentDate);

            packageRepository.updateStatusPackage(statusId, packageId, getDate);
        } else {
            throw new ValidationException("error");
        }
    }

    @CachePut
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

    @Override
    public String uploadFileInS3bucket(MultipartFile file) throws Exception {
        AwsS3Client s3Client = new AwsS3Client(Region.EU_WEST_2);
        s3Client.buildS3Client();

        s3Client.setBucketName(bucketName);
        s3Client.setFile(file);

        String response = s3Client.uploadImgFile();
        if(!response.isEmpty()){
           return response;
        } else{
            throw new Exception("error");
        }
    }

    @Cacheable(key="#username")
    @Transactional
    @Override
    public List<Packages> getCourierPackages(String username) throws Exception {
        System.out.println("not cache|extract record from db");
        if (!username.isEmpty()) {
            List<Packages> getPackages2 = this.buildWhereClause(username,4);
            return PackageHandler.getPackageList(getPackages2);
        } else {
            throw new Exception("error");
        }
    }

    @Cacheable(key = "#username")
    @Transactional
    @Override
    public List<PackageProblem> getCourierProblemPackages(String username) throws Exception {
        if (!username.isEmpty()) {
            List<PackageProblem> getPackages = packageProblemRepository.findProblemCourierPackagesByUsername(username);
            if(!getPackages.isEmpty()){

                return PackageHandler.getCourierProblemPackages(getPackages);
            }else {
                throw new Exception("error");
            }
        } else {
            throw new Exception("error");
        }
    }

    @Cacheable(key="#username")
    @Transactional
    @Override
    public List<Packages> getDeliveredPackages(String username) throws Exception {
        if (!username.isEmpty()) {
            List<Packages> getPackages = packageRepository.findDeliveredCourierPackagesByUsername(username);
            //List<Packages> getPackages2 = this.buildWhereClause(username,1);
            return PackageHandler.getPackageList(getPackages);
        } else {
            throw new Exception("error");
        }
    }

    @Override
    public List<Courier> getAllCouriersByCityName(String cityName) {
        return courierRepository.findPackagesByCityName(cityName);
    }

    @Override
    public int deleteCourierByPhoneAndUsername(String phone, String username) {
        return courierRepository.deleteByPhoneAndAccount_Username(phone,username);
    }

    @Override
    public Optional<Courier> getCourierByUsername(String username) {
        return courierRepository.findByAccount_Username(username);
    }

    @Override
    public boolean isOwner(String username, Long courierId) {

        Optional<Courier> givenCourier = courierRepository.findById(courierId);

        Optional<Courier> currentCourier = courierRepository.findByAccount_Username(username);

        if(givenCourier.isEmpty() || currentCourier.isEmpty()){
            return false;
        }

        return isCourier(currentCourier.get().getAccount()) && givenCourier.get().getAccount().getUsername().equalsIgnoreCase(username);
    }

    private boolean isCourier(User_account user_account){
       return user_account.getUserRoles()
               .stream()
               .map(Roles::getRole_description)
               .anyMatch(role -> role == Role.courier);
    }

    @Transactional
    @Override
    public Courier Login(String username) throws ValidationException {

        Optional<Courier> courier = courierRepository.findByAccount_Username(username);
        if (courier.isPresent()) {
            Courier res = new Courier();
            res.setCourier_first_name(courier.get().getCourier_first_name());
            res.setCourier_last_name(courier.get().getCourier_last_name());
            return res;
        } else {
            throw new ValidationException("error");
        }
    }

    @Override
    public Courier Update(Object object) {
        return courierRepository.save((Courier) object);
    }

    @Override
    public void Insert(Object object) {
        if(object instanceof Courier){
            courierRepository.save((Courier) object);
        }
    }

    private List<Packages> buildWhereClause(String username, int status_id){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Packages> criteriaQuery = criteriaBuilder.createQuery(Packages.class);

        Root<Packages>packagesRoot = criteriaQuery.from(Packages.class);
        Join<Packages,TypePackage> typePackageJoin = packagesRoot.join("typePackage", JoinType.INNER);
        Join<Packages, StatusPackage> statusPackageJoin = packagesRoot.join("statusPackage", JoinType.INNER);
        Join<Packages, Customer> customerJoin = packagesRoot.join("customer", JoinType.INNER);
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

    private Customer getCustomerDataByPackage(int packageId){

        Packages getCustomerPackage = packageRepository.findByPackageId((long) packageId);

        return getCustomerPackage.getCustomer();
    }
}
