package com.example.demo.services.Impl;

import com.example.demo.models.*;
import com.example.demo.private_lib.PackageHandler;
import com.example.demo.private_lib.User;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.PackageRepository;
import com.example.demo.repositories.UserAccountRepository;
import com.example.demo.services.CustomerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = {"customer"})
public class CustomerServiceImpl extends User implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CityRepository cityRepository;

    private final  PackageRepository packageRepository;
    @PersistenceContext
    private EntityManager entityManager;

    private CacheManager cacheManager;

    public CustomerServiceImpl(CustomerRepository customerRepository, CityRepository cityRepository, PackageRepository packageRepository){
        this.customerRepository = customerRepository;
        this.cityRepository = cityRepository;
        this.packageRepository = packageRepository;

    }

    @Transactional
    @Override
    public Customer Login(String username) throws ValidationException{
        User_account user_account2 = new User_account();
        user_account2.setUsername(username);
        Customer customer2 = new Customer();
        customer2.setUser_account(user_account2);
        Customer customer =  this.buildWhereClause(customer2);
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

    @CachePut
    @Transactional
    @Override
    public Customer Update(Object object) {
        return customerRepository.save((Customer)object);
    }

    @CachePut
    @Transactional
    @Override
    public void Insert(Object object) {
        if(object instanceof Customer){
            customerRepository.save((Customer) object);
        }
    }

    @Transactional
    @Override
    public Customer IsEmailExist(String email) {
        Customer customer = new Customer();
        customer.setEmail(email);
        return this.buildWhereClause(customer);
        //return customerRepository.findByEmail(email);
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
            System.out.println(getPackages);
            if(!getPackages.isEmpty()){
                return PackageHandler.getPackageList(getPackages);
            }else {
                throw new Exception("error");
            }
        } else {
            throw new Exception("error");
        }
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
     * @param customer
     * @return Customer
     */
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
    }

    public void checkCacheIsWorking(){
        CaffeineCache caffeineCache = (CaffeineCache)cacheManager .getCache("customer");
        Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();

        for (Map.Entry<Object, Object> entry : nativeCache.asMap().entrySet()) {
            System.out.println("Key = " + entry.getKey());
            System.out.println("Value = " + entry.getValue());
        }
    }
}
