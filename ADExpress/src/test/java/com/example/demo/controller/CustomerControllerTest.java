package com.example.demo.controller;

import com.example.demo.config.JwtAuthEntryPoint;
import com.example.demo.config.JwtTokenFilter;
import com.example.demo.config.JwtTokenUtil;
import com.example.demo.config.SecurityConfig;
import com.example.demo.model.Customer;
import com.example.demo.model.Packages;
import com.example.demo.model.User_account;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.services.CustomerService;
import com.example.demo.services.Impl.UserAccountServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.C;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.sql.DataSource;
import javax.transaction.Transactional;

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
    private CustomerService customerService;

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

        when(customerService.Login(username)).thenReturn(customer);

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
        given(customerService.Update(any(Customer.class))).willAnswer((invocation) -> invocation.getArgument(0));

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

        when(customerService.getAllPackages(username)).thenReturn(packagesList);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/customer/packages?username="+username)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerPackage)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(customerService).getAllPackages(username);
    }

    @Test
    public void givenCustomerEmail_whenCheckEmail_thenReturnEmailExist() throws Exception {
        String searchEmail = "email@example.com";

       when(customerService.IsEmailExist(searchEmail)).thenReturn(customer);


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
