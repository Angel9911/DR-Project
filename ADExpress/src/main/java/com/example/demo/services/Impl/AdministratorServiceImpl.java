package com.example.demo.services.Impl;

import com.example.demo.models.*;
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
    CityRepository cityRepository;
    @Autowired
    TypePackageRepository typePackageRepository;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserAccountRepository userRepository;
    Long statusPackageId = 4L; // 4 - izpratena pratka
    private final Random randomCouriers = new Random();
    protected String usernameCourier;
    protected String passwordCourier;
    @Value("${adexpress.app.courier.password}")
    private String passwordSecret;

    @CachePut(key="#courier")
    @Transactional
    @Override
    public Courier updateCourier(Courier courier) {
        return courierRepository.save(courier);
    }

    @Transactional
    @Override
    public int deleteCustomerById(int id) {
        return customerRepository.deleteByUsername(id);
    }

    @Transactional
    @Override
    public int deleteCourierByUsernamePhone(String username, String phone) {
        return courierRepository.deleteByUsernameAndPhone(username, phone);
    }

    @Cacheable
    @Transactional
    @Override
    public List<TypePackage> getAllPackagesTypes() {
        return typePackageRepository.findAllTypes();
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
        Packages registerPackage = new Packages();
        Courier getCourier = new Courier();
        if (!ObjectUtils.isEmpty(customerPackage)) {
            if (this.IfCourierHasEqualCountPackages("Бургас")) {
                System.out.println("there are not duplicated records");
                getCourier = this.getCourierByCity("Бургас");
            } else {
                System.out.println("there are duplicated records");
                getCourier = this.getRandomCourier("Бургас");
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
            System.out.println(customerRepository.findUserIdByUserInfo(customerPackage.getCustomer().getName(),customerPackage.getCustomer().getLast_name(),/*packages.getCustomer().getAddress()*/customerPackage.getCustomer().getPhone()));
            customer.setUser_id(customerRepository.findUserIdByUserInfo(customerPackage.getCustomer().getName(), customerPackage.getCustomer().getLast_name(),/*packages.getCustomer().getAddress(), */customerPackage.getCustomer().getPhone()));
            Customer receiver = new Customer();
            receiver.setUser_id(customerRepository.findUserIdByUserInfo(customerPackage.getReceiver().getName(), customerPackage.getReceiver().getLast_name(), /*packages.getReceiver().getAddress(), */customerPackage.getReceiver().getPhone()));
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

    private Courier getRandomCourier(String city) {
        int randomKey = 0;
        Map<Courier, Integer> courierList = this.getCouriers(city);
        Set<Courier> keySet = courierList.keySet();
        List<Courier> keyList = new ArrayList<>(keySet);
        int countCouriers = keyList.size();
        for (int i = 0; i < countCouriers; i++) {
            randomKey = this.randomCouriers.nextInt(countCouriers);
        }
        Courier randomCourier = keyList.get(randomKey);
        Integer randomValue = courierList.get(randomCourier);
        System.out.println("test random" + randomCourier.getCourier_phone() + " " + randomValue);
        return randomCourier;
    }

    private Boolean IfCourierHasEqualCountPackages(String city) {
        Map<Courier, Integer> courierList = this.getCouriers(city);
        List<Integer> ListCouriers = new ArrayList<>(courierList.values());
        List<Integer> duplicateList = new ArrayList<>();
        for (int i = 0; i < ListCouriers.size(); i++) {
            for (int j = 1; j < ListCouriers.size(); j++) {
                if (ListCouriers.get(i).equals(ListCouriers.get(j)) && i != j) {
                    duplicateList.add(ListCouriers.get(i));
                    break;
                }
            }
        }
        System.out.println(duplicateList.isEmpty());
        if (duplicateList.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private Courier getCourierByCity(String city) {
        Courier getCourier = new Courier();
        Map<Courier, Integer> couriers = this.getCouriers(city);
        List<Integer> sortedList = new ArrayList<>(couriers.values());
        for (Map.Entry<Courier, Integer> entry : couriers.entrySet()) {
            if (entry.getValue().equals(sortedList.get(0))) {
                System.out.println(entry.getKey().getCourier_phone());
                getCourier.setCourier_id(entry.getKey().getCourier_id());
            }
        }
        return getCourier;
    }

    private Map<Courier, Integer> getCouriers(String city) {
        Map<Courier, Integer> couriers = new HashMap<>();
        List<Courier> getCouriersByCity = courierRepository.findPackagesByCityName1(city);
        for (Courier courier : getCouriersByCity) {
            System.out.println(courier.getCourier_phone() + " " + courier.getPackagesList().size());
            couriers.put(courier, courier.getPackagesList().size());
        }
        return couriers;
    }
    private User_account createCourierAccount(String fName, String lName, String role){
        this.usernameCourier = fName.charAt(0) +lName;
        this.passwordCourier = this.usernameCourier + passwordSecret;
        User_account user_account = new User_account(this.usernameCourier,encoder.encode(this.passwordCourier));
        List<Roles> roles = new ArrayList<>();
        Roles modRole = rolesRepository.findRole_idByRole_description(role);
        roles.add(modRole);
        user_account.setUserRoles(roles);
        userRepository.save(user_account);
        return user_account;
    }
    @Transactional
    @Override
    public Administrator Login(String username) throws ValidationException {
        Administrator administrator = administratorRepository.findAdministratorByUsername(username);
        Administrator result = new Administrator();
        if (administrator != null) {
            User_account user_account = new User_account();
            user_account.setUsername(administrator.getUser_account_administrator().getUsername());
            result.setUser_account_administrator(user_account);
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
            return courierRepository.save((Courier) object);
        }
        return object;
    }

    @Transactional
    @Override
    public void Insert(Object object) {
        if(object instanceof Customer){
            Customer customer = (Customer)object;
            customerRepository.save(customer);
        }
        if(object instanceof Courier){
            Courier courier = (Courier)object;
            User_account getAccount = this.createCourierAccount(courier.getCourier_first_name(),courier.getCourier_last_name(),"courier");
            courier.setUser_account_courier(getAccount);
             courierRepository.save(courier);
        }
    }


}
