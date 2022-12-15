package com.example.demo.services;

import com.example.demo.model.*;
import com.example.demo.private_lib.PackageHandler;
import com.example.demo.private_lib.User;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.PackageRepository;
import com.example.demo.repository.UserAccountRepository;
import org.hibernate.jpa.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService extends User {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    UserAccountRepository userAccountRepository;
    private User_account user_account;
    @PersistenceContext
    private EntityManager entityManager;
    protected Package aPackage;

    @Transactional
    @Override
    public Customer Login(String username) throws ValidationException{
        User_account user_account2 = new User_account();
        user_account2.setUsername(username);
        Customer customer2 = new Customer();
        customer2.setUser_account(user_account2);
        Customer customer =  this.buildWhereClause(customer2);
        //Customer customer = customerRepository.findUserByUsernameAndPassword(username);
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
    @Override
    public Customer Update(Object object) {
        return customerRepository.save((Customer)object);
    }

    @Transactional
    @Override
    public void Insert(Object object) {
        if(object instanceof Customer){
            customerRepository.save((Customer) object);
        }
    }

    @Transactional
    public Customer IsEmailExist(String email) {
        Customer customer = new Customer();
        customer.setEmail(email);
        return this.buildWhereClause(customer);
        //return customerRepository.findByEmail(email);
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
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Transactional
    public City getCityIdByName(String name) {
        return cityRepository.findCityNameById(name);
    }

    public Customer buildWhereClause(Customer customer){
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
}
