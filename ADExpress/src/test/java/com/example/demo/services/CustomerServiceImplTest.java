package com.example.demo.services;

import com.example.demo.models.*;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.PackageRepository;
import com.example.demo.services.Impl.CustomerServiceImpl;
import org.assertj.core.api.OptionalIntAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerServiceImpl;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    PackageRepository packageRepository;
    @Autowired
    CacheManager cacheManager;

    private final List<Packages> packagesList = new ArrayList<>();
    private final Customer customer = new Customer();


    @Before
    public void init() {
        TypePackage typePackage = new TypePackage();
        typePackage.setType_name("Колет");
        typePackage.setType_id(1L);
        StatusPackage statusPackage = new StatusPackage();
        statusPackage.setStatus_type("Мотопед");
        statusPackage.setStatus_id(1L);

        customer.setName("Angel");
        customer.setLast_name("Dimitrov");
        customer.setPhone("0885215123");
        customer.setCity("Варна");
        customer.setAddress("kv. Koprivchica, nomer 5");
        customer.setEmail("email@example.com");
        User_account user_account = new User_account();
        user_account.setUsername("test-customer");
        user_account.setPassword("test-123");
        customer.setUser_account(user_account);

        Packages customerPackage = new Packages();
        customerPackage.setPackage_id(1L);
        customerPackage.setName_package("Часовник");
        customerPackage.setSize_height(20);
        customerPackage.setSize_width(20);
        customerPackage.setTypePackage(typePackage);
        customerPackage.setStatusPackage(statusPackage);
        customerPackage.setCustomer(customer);

        Packages customerPackage2 = new Packages();
        customerPackage2.setPackage_id(2L);
        customerPackage2.setName_package("Мотопед");
        customerPackage2.setSize_height(30);
        customerPackage2.setSize_width(40);
        customerPackage2.setTypePackage(typePackage);
        customerPackage2.setStatusPackage(statusPackage);
        customerPackage2.setCustomer(customer);

        packagesList.add(customerPackage);
        packagesList.add(customerPackage2);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenUsernameIsProvided_thenReturnListOfPackages() throws Exception {
        String username = "test-customer";

        when(packageRepository.findPackagesByUser_accountUsername(username)).thenReturn(packagesList);

        List<Packages> fetchedPackages = customerServiceImpl.getAllPackages(username);

        Assert.assertSame(fetchedPackages.get(0).getName_package(),packagesList.get(0).getName_package());
        Assert.assertSame(fetchedPackages.get(0).getCustomer().getName(),packagesList.get(0).getCustomer().getName());
        Assert.assertEquals(fetchedPackages.size(),packagesList.size());
    }

    @Test
    public void whenEmailIsProvided_thenCheckIfExist() {
        when(customerRepository.findByEmail("email@example.com")).thenReturn(customer);

        Customer fetchedCustomer = customerServiceImpl.IsEmailExist("email@example.com");
        Assert.assertEquals("The email exist",fetchedCustomer.getName(),customer.getName());
    }
    @Test
    public void savedCustomer_Success() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer savedCustomer = customerRepository.save(customer);

        Assert.assertNotNull(savedCustomer.getName());
    }
    @Test
    public void getAllCustomersShouldReturnListOfCustomers() throws Exception {
        CustomerServiceImpl moc2 = org.mockito.Mockito.mock(CustomerServiceImpl.class);

        when(customerRepository.findAll()).thenReturn(List.of(new Customer(),new Customer(),new Customer(),new Customer(),new Customer()));
        List<Customer> customerList = customerServiceImpl.getAllCustomers();
        System.out.println(customerList.size());

        Assert.assertNotNull(customerList.size());
        Assert.assertEquals(5,customerList.size());
    }


}
