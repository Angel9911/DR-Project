package com.example.demo.services;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.xml.bind.ValidationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AdministratorService {
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
    private City getCity;

    @Transactional
    public Courier createCourier(Courier courier) throws ValidationException {
        if (courier != null) {
            Courier result = new Courier();
            result.setCourier_first_name(courier.getCourier_first_name());
            result.setCourier_last_name(courier.getCourier_last_name());
            result.setCourier_phone(courier.getCourier_phone());
            User_account getAccount = this.createCourierAccount(courier.getCourier_first_name(),courier.getCourier_last_name(),"courier");
            result.setCourier_city_name(courier.getCourier_city_name());
            courier.setUser_account_courier(getAccount);
            return courierRepository.save(courier);
        } else {
            throw new ValidationException("error");
        }
    }

    @Transactional
    public Customer createCustomer(Customer customer) throws ValidationException {
        if (customer != null) {
            Customer result = new Customer();
            result.setName(customer.getName());
            result.setLast_name(customer.getLast_name());
            String getCity = customer.getCity();
        /* City city=new City();
        city.setCity_id(customerService.getCityIdByName(getCity).getCity_id()); */
            result.setCity(getCity);
            result.setEmail(customer.getEmail());
            result.setAddress(customer.getAddress());
            result.setPhone(customer.getPhone());
            return customerRepository.save(customer);
        } else {
            throw new ValidationException("error");
        }
    }

    @Transactional
    public Courier updateCourier(Courier courier) {
        return courierRepository.save(courier);
    }

    @Transactional
    public Administrator LoginAdministrator(String username) throws ValidationException {
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

    @Transactional
    public int deleteCustomer(int id) {
        return customerRepository.deleteByUsername(id);
    }

    @Transactional
    public int deleteCourier(String username, String phone) {
        courierRepository.deleteByUsernameAndPhone(username, phone);
        return 0;
    }

    @Transactional
    public List<TypePackage> getAllTypes() {
        return typePackageRepository.findAllTypes();
    }

    @Transactional
    public List<Packages> findPackagesByCustomerPhone(String phone) {
        return packageRepository.findPackagesByCustomerPhone(phone);
    }

    @Transactional
    public List<Packages> getAllPackages() {
        List<Packages> packagesList = packageRepository.findAll();
        List<Packages> newListPackages = new ArrayList<>();
        for (Packages packages:packagesList) {
            Packages getPackage = new Packages();
            getPackage.setName_package(packages.getName_package());
            StatusPackage statusPackage = new StatusPackage();
            statusPackage.setStatus_type(packages.getStatusPackage().getStatus_type());
            TypePackage typePackage = new TypePackage();
            typePackage.setType_name(packages.getTypePackage().getType_name());
            getPackage.setStatusPackage(statusPackage);
            getPackage.setTypePackage(typePackage);
            getPackage.setPackage_price(packages.getPackage_price());
            getPackage.setTotal_cost(packages.getTotal_cost());
            Customer customer = new Customer();
            customer.setName(packages.getCustomer().getName());
            customer.setLast_name(packages.getCustomer().getLast_name());
            customer.setPhone(packages.getCustomer().getPhone());
            customer.setAddress(packages.getCustomer().getAddress());
            getPackage.setCustomer(customer); //  moje bi trqbva da se promeni na receiver
            if(packages.getDate_register_package()!=null){
                Date registerDate = this.getDate(packages.getDate_register_package());
                getPackage.setDate_register_package(registerDate);
            }
            //Date registerDate = this.getDate(packages.getDate_register_package());
            if(packages.getDate_delivery_package()!=null){
                Date deliveryDate = this.getDate(packages.getDate_delivery_package());
                getPackage.setDate_delivery_package(deliveryDate);
            }
            //Date deliveryDate = this.getDate(packages.getDate_delivery_package());
          //  getPackage.setDate_register_package(registerDate);
         //   getPackage.setDate_delivery_package(deliveryDate);
            getPackage.setSize_height(0);
            getPackage.setSize_width(0);
            getPackage.setReview_package(false);
            newListPackages.add(getPackage);
        }
        return newListPackages;
    }

    @Transactional
    public Packages registerPackage(Packages packages) {
        Packages result = new Packages();
        Courier getCourier = new Courier();
        if (!ObjectUtils.isEmpty(packages)) {
            // String getDeliveryCity = packages.getCustomer().getCity();
            if (this.IfCourierHasEqualCountPackages("Бургас")) {
                System.out.println("there are not duplicated records");
                getCourier = this.getCourierByCity("Бургас");
            } else {
                System.out.println("there are duplicated records");
                getCourier = this.getRandomCourier("Бургас");
            }
            System.out.println(packages.getName_package());
            result.setName_package(packages.getName_package());
            StatusPackage statusPackage = new StatusPackage();
            statusPackage.setStatus_id(statusPackageId);
            result.setStatusPackage(statusPackage);
            System.out.println(result.getStatusPackage().getStatus_id());
            TypePackage typePackage = new TypePackage();
           // System.out.println(typePackageRepository.findTypeIdByName(packages.getTypePackage().getType_name()));
            typePackage.setType_id(typePackageRepository.findTypeIdByName(packages.getTypePackage().getType_name()));
            Customer customer = new Customer();
            System.out.println(customerRepository.findUserIdByUserInfo(packages.getCustomer().getName(),packages.getCustomer().getLast_name(),/*packages.getCustomer().getAddress()*/packages.getCustomer().getPhone()));
            customer.setUser_id(customerRepository.findUserIdByUserInfo(packages.getCustomer().getName(), packages.getCustomer().getLast_name(),/*packages.getCustomer().getAddress(), */packages.getCustomer().getPhone()));
            Customer receiver = new Customer();
            receiver.setUser_id(customerRepository.findUserIdByUserInfo(packages.getReceiver().getName(), packages.getReceiver().getLast_name(), /*packages.getReceiver().getAddress(), */packages.getReceiver().getPhone()));
            result.setCustomer(customer);
            result.setReceiver(receiver);
            result.setTypePackage(typePackage);
            LocalDate getCurrentDate = java.time.LocalDate.now();
            java.sql.Date getDate = java.sql.Date.valueOf(getCurrentDate);
            result.setDate_register_package(getDate);
            result.setPackage_price(packages.getPackage_price());
          //  System.out.println(getCourier.getCourier_id());
            result.setCourier(getCourier);
        }
        return packageRepository.save(result);
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
        // return !duplicateList.isEmpty();
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
        //List<Integer> sortedList = new ArrayList<>(couriers.values());
        //Collections.sort(sortedList);
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
    private Date getDate(Date datePackage){
        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String packageDate = outputFormatter.format(datePackage); // Output : 01/20/2012
        System.out.println(packageDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate localdate = LocalDate.parse(packageDate, formatter);
        return java.sql.Date.valueOf(localdate);
    }
}
