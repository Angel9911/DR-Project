package com.example.demo.services;

import com.example.demo.model.*;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.PackageRepository;
import com.example.demo.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    UserAccountRepository userAccountRepository;
    private User_account user_account;

    @Transactional
    public void insertCustomer(Customer customer) {
          /*  this.user_account = new User_account();
            user_account.setUsername(customer.getUser_account().getUsername());
            user_account.setPassword(customer.getUser_account().getPassword());
            //  accountService.insertAccount(user_account);
            Customer result = new Customer();
            result.setName(customer.getName());
            result.setLast_name(customer.getLast_name());
            result.setCity(customer.getCity());
       /* City city = new City();
        city.setCity_id(customer.getCity().getCity_id());
        result.setCity(city);
            result.setEmail(customer.getEmail());
            result.setAddress(customer.getAddress());
            result.setPhone(customer.getPhone()); */
        customerRepository.save(customer);
    }

    @Transactional
    public Customer LoginCustomerByUsername(String username) throws ValidationException {
        Customer customer = customerRepository.findUserByUsernameAndPassword(username);
        Customer res = new Customer();
        if (customer != null) {
            res.setUser_id(customer.getUser_id());
            res.setName(customer.getName());
            res.setLast_name(customer.getLast_name());
            res.setCity(customer.getCity());
            res.setEmail(customer.getEmail());
            res.setAddress(customer.getAddress());
            res.setPhone(customer.getPhone());
            User_account user_account = customer.getUser_account();
            res.setUser_account(user_account);
        } else {
            throw new ValidationException("error");
        }
        return res;
    }

    @Transactional
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer IsEmailExist(String email) {
        return customerRepository.findByEmail(email);
    }

    @Transactional
    public List<Customer> getAllCustomers() throws Exception {
        List<Customer> customerList = customerRepository.findAll();
        if (customerList.isEmpty()) {
            throw new Exception("error");
        } else {
            return customerList;
        }
    }

    @Transactional
    public List<Packages> getAllPackages(String username) throws Exception {
        if (!username.isEmpty()) {
            List<Packages> getPackages = packageRepository.findPackagesByUser_accountUsername(username);
            if (getPackages.isEmpty()) {
                throw new Exception("error");
            } else {
                List<Packages> resultPackages = new ArrayList<>();
                System.out.println(getPackages.size());
                for (Packages packages : getPackages) {
                    System.out.println(packages.getName_package());
                    Packages getPackage = new Packages();
                    getPackage.setName_package(packages.getName_package());
                    TypePackage typePackage = new TypePackage();
                    typePackage.setType_name(packages.getTypePackage().getType_name());
                    getPackage.setTypePackage(typePackage);
                   // if(packages.getOffice().getOffice_id() != null){
                  //      Office office = new Office();
                       // office.setOffice_location(packages.getOffice().getOffice_location());
                      //  getPackage.setOffice(office);
                  //  } else if(packages.getCustomer().getAddress() != null){
                        Customer customer = new Customer();
                        customer.setAddress(packages.getCustomer().getAddress());
                        getPackage.setCustomer(customer);
                 //   }
                    StatusPackage statusPackage = new StatusPackage();
                    statusPackage.setStatus_type(packages.getStatusPackage().getStatus_type());
                    getPackage.setStatusPackage(statusPackage);
                    /*
                    don't need these values
                     */
                    getPackage.setPackage_price(packages.getPackage_price());
                    getPackage.setTotal_cost(packages.getTotal_cost());
                    resultPackages.add(packages);
                }
                return resultPackages;
            }
        } else {
            throw new Exception("error");
        }
    }

    /*  @Transactional
      public int updateAddressByUsername(String address,int user_id){
          return customerRepository.updateAddresss(address,user_id);
      }
      @Transactional
      public int updatePhoneByUserID(String phone,int user_id){
          return customerRepository.updatePhone(phone,user_id);
      }
      */
    @Transactional
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Transactional
    public City getCityById(int id) {
        return cityRepository.findCityById(id);
    }

    @Transactional
    public City getCityIdByName(String name) {
        return cityRepository.findCityNameById(name);
    }
    /* @Transactional
    public Optional findByToken(String token) {
      /*  Optional customer= customerRepository.findByToken(token);
        if(customer.isPresent()){
            Customer customer1 = (Customer) customer.get();
            User user= new User(customer1.getUser_account().getUsername(), customer1.getUser_account().getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("user"));
            return Optional.of(user);
        }
        return  Optional.empty();
    } */
  /*  @Transactional
    public Customer getCustomer(Integer user_id){
       return customerRepository.findAllByUser_Id(user_id);
    }*/
}
