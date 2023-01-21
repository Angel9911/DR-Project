package com.example.demo.controllers;

import com.example.demo.models.Customer;
import com.example.demo.models.Packages;
import com.example.demo.models.User_account;
import com.example.demo.services.Impl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CustomerControllerTest {

    @Mock
    private CustomerServiceImpl customerServiceImpl;

    private MockMvc mockMvc;

    @InjectMocks
    private CustomerController customerController;

    private Packages customerPackage;
    private Customer customer;
    private List<Packages> packagesList;
    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        customerPackage = new Packages();
        customerPackage.setPackage_id(1L);
        customerPackage.setName_package("Часовник");
        customerPackage.setSize_height(20);
        customerPackage.setSize_width(20);

        customer = new Customer();
        customer.setUser_id(1L);
        customer.setName("Angel");
        customer.setLast_name("Dimitrov");
        customer.setPhone("0885215123");
        customer.setCity("Varna");
        customer.setAddress("kv. Koprivchica, nomer 5");
        customer.setEmail("email@example.com");
        User_account user_account = new User_account();
        user_account.setUsername("test-customer");
        user_account.setPassword("test-123");
        customer.setUser_account(user_account);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void shouldFindCustomerObjectByUsername() throws Exception{
        String username = "test-customer";

        when(customerServiceImpl.Login(username)).thenReturn(customer);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/customer/{username}",username)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(customer)))
                .andReturn();
    }

    @Test
    public void givenCustomerObject_whenUpdateCustomer_thenReturnUpdateObject() throws Exception {
        customer.setName("Ivan");
        customer.setLast_name("Dimitrov");
        given(customerServiceImpl.Update(any(Customer.class))).willAnswer((invocation) -> invocation.getArgument(0));

        mockMvc.perform(
                MockMvcRequestBuilders.put("/customer/update",customer)
                .content(asJsonString(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(customer)))
                .andReturn();
    }

    @Test
    public void shouldFetchCustomerPackagesByUsername() throws Exception {
        String username = "test-customer";

        when(customerServiceImpl.getAllPackages(username)).thenReturn(packagesList);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/customer/packages?username="+username)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerPackage)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(customerServiceImpl).getAllPackages(username);
    }

    @Test
    public void givenCustomerEmail_whenCheckEmail_thenReturnEmailExist() throws Exception {
        String searchEmail = "email@example.com";

       when(customerServiceImpl.IsEmailExist(searchEmail)).thenReturn(customer);


       mockMvc.perform(
               MockMvcRequestBuilders.get("/customer?email="+searchEmail).accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(MockMvcResultMatchers.content().string("true"))
               .andReturn();
    }
    public static String asJsonString(final Object o){
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
