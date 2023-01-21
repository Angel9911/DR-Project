package com.example.demo.services;

import com.example.demo.models.Customer;
import com.example.demo.models.Packages;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.services.Impl.CourierServiceImpl;
import com.example.demo.services.Impl.CustomerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CourierServiceTest {

    @Autowired
    CourierServiceImpl courierServiceImpl;
    @Autowired
    CustomerServiceImpl customerServiceImpl;
    @Autowired
    private CustomerRepository customerRepository;
    @Mock
    Customer customer;
    @Mock
    List<Packages> list;

    @Test
    public void whenUserIdIsProvided_thenRetrievedNameIsCorrect() throws Exception {
    }
}
