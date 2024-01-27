package com.example.demo.services.Impl;

import com.example.demo.models.entity.City;
import com.example.demo.models.entity.Customer;
import com.example.demo.models.entity.Packages;
import com.example.demo.models.views.CustomerView;
import com.example.demo.private_lib.PackageHandler;
import com.example.demo.private_lib.async_tasks.verification_address.SmartyStreetVerification;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.PackageRepository;
import com.example.demo.services.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.xml.bind.ValidationException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@CacheConfig(cacheNames = {"customer"})
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CityRepository cityRepository;

    private final  PackageRepository packageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private CacheManager cacheManager;

    private final SmartyStreetVerification verificationAddress;

    public CustomerServiceImpl(CustomerRepository customerRepository, CityRepository cityRepository, PackageRepository packageRepository, SmartyStreetVerification verificationAddress){
        this.customerRepository = customerRepository;
        this.cityRepository = cityRepository;
        this.packageRepository = packageRepository;
        this.verificationAddress = verificationAddress;
    }

    @Transactional
    @Override
    public CustomerView Login(Long customerId) throws ValidationException{
        return this.customerRepository.findByUserId(customerId);
    }

    @Transactional
    @Override
    public CustomerView getCustomerDetails(String username) throws ValidationException {
        return customerRepository.findByAccount_Username(username);
    }

    @Override
    public long getCustomerIdByUsersInfo(String name, String lastName, String phone) {
        return customerRepository.findUserIdByUserInfo(name,lastName,phone);

    }

    @Override
    public Optional<Customer> findCustomerById(int customerId) {
        return customerRepository.findById((long) customerId);
    }

    @CachePut
    @Transactional
    @Override
    public Customer Update(Customer customer)throws EntityNotFoundException {

        Optional<Customer> isExistsCustomer = customerRepository.findById(customer.getUserId());

        if(isExistsCustomer.isEmpty()){
            throw new EntityNotFoundException("Customer not found");
        }
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public int deleteCustomerByCustomerId(int customerId) {
        return customerRepository.deleteByUserId(customerId);
    }

    @Override
    public boolean isOwner(String currentUsername, Long givenCustomerId) {

        Optional<Customer> givenCustomer = customerRepository.findById(givenCustomerId);

        if(givenCustomer.isEmpty() || currentUsername == null){
            return false;
        }

        return givenCustomer.get().getAccount().getUsername().equalsIgnoreCase(currentUsername);
    }

    @CachePut
    @Transactional
    @Override
    public void Insert(Object object) {
        if(object instanceof Customer){
            try {
                System.out.println(this.extracted().get()); // TODO: Here we should check if the address is valid, when the user create its account.
            } catch (InterruptedException | ExecutionException | UnsupportedEncodingException | JsonProcessingException e) {
                e.printStackTrace();
            }
            customerRepository.save((Customer) object);
        }
    }

    @Transactional
    @Override
    public Optional<Customer> IsEmailExist(String email) {
        return customerRepository.findByEmail(email);
    }

    @Cacheable
    @Transactional
    @Override
    public List<Customer> getAllCustomers() throws Exception {
        List<Customer> customerList = customerRepository.findAll();
        if (customerList.isEmpty()) {
            throw new Exception("error");
        } else {
            return customerList;
        }
    }

    @Cacheable(key="#username")
    @Transactional
    @Override
    public List<Packages> getAllPackages(String username) throws Exception {

        System.out.println("Getting All the users from DB! | Not Cached");

        if (!username.isEmpty()) {

            List<Packages> getPackages = packageRepository.findPackagesByUser_accountUsername(username);

            if(!getPackages.isEmpty()){

                return PackageHandler.getPackageList(getPackages);
            }else {

                throw new Exception("not found packages");
            }
        } else {
            throw new Exception("not found username");
        }
    }

    public CompletableFuture<Boolean> extracted() throws UnsupportedEncodingException, JsonProcessingException {
        CompletableFuture<Boolean> isAddressValid = new CompletableFuture<>();

        verificationAddress.validateAddress("Varna", "Mir 3")
                .whenCompleteAsync((result, throwable) -> {
                    if (throwable != null) {
                        // Handle the exception
                        System.err.println("Error in address validation: " + throwable.getMessage());
                        isAddressValid.completeExceptionally(throwable); // Complete exceptionally without blocking the main thread
                    } else {
                        // Handle the result
                        System.out.println("Is address valid? " + result);
                        isAddressValid.complete(result);
                    }
                });

        return isAddressValid;
    }


    @Transactional
    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Transactional
    @Override
    public City getCityIdByName(String name) {
        return cityRepository.findCityNameById(name);
    }

    /**
     * Its used to build dynamic set of parameters by which to retrieve a Customer object.
     * In this way, the queries findUserByUsernameAndPassword and findByEmail in customerRepository are useless.
     * This method replaces them.
     * @return Customer
     */
    /*
    private Customer buildWhereClause(Customer customer){
        //EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "" );
        //EntityManager entitymanager = emfactory.createEntityManager( );
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> customerCriteriaQuery = criteriaBuilder.createQuery(Customer.class);

        Root<Customer> customerRoot = customerCriteriaQuery.from(Customer.class);
        Join<Customer,User_account> userAccountJoin = customerRoot.join("user_account_customer", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        if(customer.getEmail()!=null){
            predicates.add(criteriaBuilder.equal(customerRoot.get("email"),customer.getEmail()));
        }
        else if(customer.getUser_account().getUsername()!=null){
            predicates.add(criteriaBuilder.equal(userAccountJoin.get("username"),customer.getUser_account().getUsername()));
        }
       // customerCriteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

      //  customerCriteriaQuery.select(criteriaBuilder.construct(Customer.class));

        TypedQuery<Customer> typedQuery = entityManager.createQuery(customerCriteriaQuery
                .select(customerRoot)
                .where(predicates.toArray(new Predicate[] {})));
        return typedQuery.getSingleResult();
    } */

}
