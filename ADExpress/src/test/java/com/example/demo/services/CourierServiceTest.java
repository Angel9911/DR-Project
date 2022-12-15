package com.example.demo.services;

import com.example.demo.model.Customer;
import com.example.demo.model.Packages;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.services.CourierService;
import com.example.demo.services.CustomerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CourierServiceTest {

    @Autowired
    CourierService courierService;
    @Autowired
    CustomerService customerService;
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
