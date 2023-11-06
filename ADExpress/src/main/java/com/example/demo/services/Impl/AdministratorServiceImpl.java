package com.example.demo.services.Impl;

import com.example.demo.models.entity.*;
import com.example.demo.models.enums.Role;
import com.example.demo.private_lib.CourierHandler;
import com.example.demo.private_lib.PackageHandler;
import com.example.demo.private_lib.User;
import com.example.demo.repositories.*;
import com.example.demo.services.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.util.*;

@Service
@CacheConfig(cacheNames = {"administrator"})
public class AdministratorServiceImpl extends User implements AdministratorService {
    @Autowired
    AdministratorRepository administratorRepository;
   @Autowired
    CourierRepository courierRepository;
   @Autowired
    CustomerRepository customerRepository;
   @Autowired
    RolesRepository rolesRepository;
    @Autowired
    TypePackageRepository typePackageRepository;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    PasswordEncoder encoder;
    //instead using directly repositories different of the repository which is responsible for the specific service
    // we will try to use the services which we need
    private final CustomerServiceImpl customerService;

    private final CourierServiceImpl courierService;
    @Autowired
    UserAccountRepository userRepository;
    Long statusPackageId = 4L; // 4 - izpratena pratka
    private final Random randomCouriers = new Random();
    protected String usernameCourier;
    protected String passwordCourier;
    @Value("${adexpress.app.courier.password}")
    private String passwordSecret;

    public AdministratorServiceImpl(CustomerServiceImpl customerService, CourierServiceImpl courierService) {
        this.customerService = customerService;
        this.courierService = courierService;
    }

    @CachePut(key="#courier")
    @Transactional
    @Override
    public Courier updateCourier(Courier courier) {
        //return courierRepository.save(courier);
        return courierService.Update(courier);
    }

    @Override
    public boolean isOwner(String currentAdminUsername, Long givenAdminId) {

        Optional<Administrator> givenAdmin = administratorRepository.findById(givenAdminId);

        Optional<Administrator> currentAdmin = administratorRepository.findByAccountAdmin_Username(currentAdminUsername);

        if(givenAdmin.isEmpty() || currentAdmin.isEmpty()){
            return false;
        }

        return isAdmin(currentAdmin.get().getAccountAdmin()) && givenAdmin.get().getAccountAdmin().getUsername().equalsIgnoreCase(currentAdminUsername);
    }

    private boolean isAdmin(User_account administrator) {
       return administrator.getUserRoles()
                .stream()
                .map(Roles::getRole_description)
                .anyMatch(role -> role == Role.administrator);
    }

    @Transactional
    @Override
    public int deleteCustomerById(int id) {
        return customerRepository.deleteByUsername(id);
    }

    @Transactional
    @Override
    public int deleteCourierByUsernamePhone(String username, String phone) {
        return courierRepository.deleteByPhoneAndAccount_Username(phone,username);
    }

    @Cacheable
    @Transactional
    @Override
    public List<TypePackage> getAllPackagesTypes() {
        return typePackageRepository.findAllTypes();
    }

    @Transactional
    @Override
    public Optional<Integer> findCustomerById(int customerId) {
        return customerRepository.findByUser_id(customerId);
    }

    @Transactional
    @Override
    public Optional<Courier> findCourierByUsername(String username) {
        return courierRepository.findByAccount_Username(username);
    }

    @Transactional
    @Override
    public List<Packages> findPackagesByCustomerPhone(String phone) {
        return packageRepository.findPackagesByCustomerPhone(phone);
    }

    @Cacheable
    @Transactional
    @Override
    public List<Packages> getAllPackages() throws Exception {
        System.out.println("not cache|extract record from db");

        List<Packages> packagesList = packageRepository.findAll();
        if(!packagesList.isEmpty()){
            return PackageHandler.getPackageList(packagesList);
        }else {
            throw new Exception("error");
        }
    }

    @Cacheable(key="#customerPackage.customer")
    @Transactional
    @Override
    public Packages registerPackage(Packages customerPackage) {
        Map<Courier, Integer> couriers = this.getCouriers("Бургас");


        Packages registerPackage = new Packages();
        Courier getCourier = new Courier();
        if (!ObjectUtils.isEmpty(customerPackage)) {
            if (CourierHandler.IfCourierHasEqualCountPackages(couriers)) {
                System.out.println("there are not duplicated records");
                getCourier = CourierHandler.getCourierByCity(couriers);
            } else {
                System.out.println("there are duplicated records");
                getCourier = CourierHandler.getRandomCourier(couriers);
            }
            System.out.println(customerPackage.getName_package());
            registerPackage.setName_package(customerPackage.getName_package());
            StatusPackage statusPackage = new StatusPackage();
            statusPackage.setStatus_id(statusPackageId);
            registerPackage.setStatusPackage(statusPackage);
            System.out.println(registerPackage.getStatusPackage().getStatus_id());
            TypePackage typePackage = new TypePackage();
            typePackage.setType_id(typePackageRepository.findTypeIdByName(customerPackage.getTypePackage().getType_name()));
            Customer customer = new Customer();
            System.out.println(customerRepository.findUserIdByUserInfo(customerPackage.getCustomer().getName(),customerPackage.getCustomer().getLastName(),/*packages.getCustomer().getAddress()*/customerPackage.getCustomer().getPhone()));
            customer.setUserId(customerRepository.findUserIdByUserInfo(customerPackage.getCustomer().getName(), customerPackage.getCustomer().getLastName(),/*packages.getCustomer().getAddress(), */customerPackage.getCustomer().getPhone()));
            Customer receiver = new Customer();
            receiver.setUserId(customerRepository.findUserIdByUserInfo(customerPackage.getReceiver().getName(), customerPackage.getReceiver().getLastName(), /*packages.getReceiver().getAddress(), */customerPackage.getReceiver().getPhone()));
            registerPackage.setCustomer(customer);
            registerPackage.setReceiver(receiver);
            registerPackage.setTypePackage(typePackage);
            LocalDate getCurrentDate = java.time.LocalDate.now();
            java.sql.Date getDate = java.sql.Date.valueOf(getCurrentDate);
            registerPackage.setDate_register_package(getDate);
            registerPackage.setPackage_price(customerPackage.getPackage_price());
            registerPackage.setCourier(getCourier);
        }
        return packageRepository.save(registerPackage);
        // return null;
    }


    private Map<Courier, Integer> getCouriers(String city) {
        Map<Courier, Integer> couriers = new HashMap<>();
        List<Courier> getCouriersByCity = courierRepository.findPackagesByCityName(city);
        for (Courier courier : getCouriersByCity) {
            System.out.println(courier.getPhone() + " " + courier.getPackagesList().size());
            couriers.put(courier, courier.getPackagesList().size());
        }
        return couriers;
    }

    @Transactional
    @Override
    public Administrator Login(String username) throws ValidationException {
        Administrator administrator = administratorRepository.findAdministratorByUsername(username);
        Administrator result = new Administrator();
        if (administrator != null) {
            User_account user_account = new User_account();
            user_account.setUsername(administrator.getAccountAdmin().getUsername());
            result.setAccountAdmin(user_account);
            Office office = new Office();
            office.setOffice_location(administrator.getOffice_administrator().getOffice_location());
            City city = new City();
            city.setCity_name(administrator.getOffice_administrator().getCity().getCity_name());
            office.setCity(city);
            result.setOffice_administrator(office);
        } else {
            throw new ValidationException("error");
        }
        return result;
    }

    @Override
    public Object Update(Object object) {
       /* if(object instanceof Customer){

        } */
        if(object instanceof Courier){
            return courierService.Update(object);//courierRepository.save((Courier) object);
        }
        return object;
    }

    @Transactional
    @Override
    public void Insert(Object object) {
        if(object instanceof Customer){
            Customer customer = (Customer)object;
            customerService.Insert(customer);
            //customerRepository.save(customer); replace with service
        }
        if(object instanceof Courier){
            Courier courier = (Courier)object;

            List<Roles> roles = new ArrayList<>();

            Roles modRole = rolesRepository.findRole_idByRole_description("courier");

            roles.add(modRole);

            User_account getAccount = CourierHandler.createCourierAccount(courier.getCourier_first_name(),courier.getCourier_last_name(),roles,encoder);
            userRepository.save(getAccount);

            courier.setAccount(getAccount);
            //courierRepository.save(courier);
            courierService.Insert(courier);
        }
    }


}
