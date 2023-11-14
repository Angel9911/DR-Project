package com.example.demo.controllers;

import com.example.demo.models.dtos.CustomerDto;
import com.example.demo.models.entity.*;
import com.example.demo.models.enums.Role;
import com.example.demo.repositories.*;
import com.example.demo.services.Impl.UserAccountImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "testusername", password = "Testpassword=987654", roles = {"USER"})
@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.example.demo.config")
class CustomerControllerTest {
    Logger logger = LoggerFactory.getLogger(CustomerControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private TypePackageRepository typePackageRepository;

    @Autowired
    private CacheManager cacheManager;

    private Customer customer;
    private User_account user_account;
    private List<Packages> testCustomerShipments;
    private Office office;
    @BeforeEach
    void setup(){
        testCustomerShipments = new ArrayList<>();
        generateStatusPackage();
        generateTypePackage();
        generateOffice();
        getUser_account();
        generateCustomerObject();
        cacheManager.getCache("customer").clear();

        generatePackage();

    }

    @Transactional
     void generatePackage() {
        Packages packages = new Packages();

        packages.setName_package("test");
        StatusPackage status = new StatusPackage();
        status.setStatusId(4L);
        packages.setStatusPackage(status);
        TypePackage type = new TypePackage();
        type.setTypeId(1L);
        packages.setStatusPackage(status);
        packages.setTypePackage(type);
        Courier courier = new Courier();

        courier.setCourier_first_name("Nencho");
        courier.setCourier_last_name("Kanchev");
        courier.setPhone("0881525522");
        courier.setCourier_city_name("Бургас");
        packages.setCustomer(this.customer);
        packages.setReceiver(this.customer);
        packages.setCourier(courier);
        packages.setOffice(this.office);

        testCustomerShipments.add(packages);

        packageRepository.save(packages);
    }

    @AfterEach
    void tearDown(){

        customerRepository.deleteAll();
        statusRepository.deleteAll();
        packageRepository.deleteAll();
    }

    @Test
    public void shouldReturnSuccessfullyInsertedCustomer() throws Exception {
        CustomerDto customerDto = this.generateCustomerDto();
        
        customerDto.setUserId(null); // because the id is autoincrement

        String dtoToJson = objectMapper.writeValueAsString(customerDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/customer/create")
                        .content(dtoToJson)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.userId").isNotEmpty())
                        .andExpect(jsonPath("$.userId").isNumber())
                        .andExpect(jsonPath("$.name").value(customerDto.getFirstName()))
                        .andExpect(jsonPath("$.lastName").value(customerDto.getLastName()))
                        .andExpect(jsonPath("$.email").value(customerDto.getEmail()))
                        .andExpect(jsonPath("$.phone").value(customerDto.getPhone()))
                        .andExpect(jsonPath("$.city").value(customerDto.getCity()))
                        .andExpect(jsonPath("$.address").value(customerDto.getAddress()));
    }

    @Test
    public void shouldReturnSuccessfullyUpdatedCustomer() throws Exception {

        CustomerDto customerDto = this.generateCustomerDto();

        String dtoToJson = objectMapper.writeValueAsString(customerDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/customer/update")
                        .content(dtoToJson)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value(customerDto.getFirstName()))
                        .andExpect(jsonPath("$.lastName").value(customerDto.getLastName()))
                        .andExpect(jsonPath("$.email").value(customerDto.getEmail()))
                        .andExpect(jsonPath("$.phone").value(customerDto.getPhone()))
                        .andExpect(jsonPath("$.city").value(customerDto.getCity()))
                        .andExpect(jsonPath("$.address").value(customerDto.getAddress()));
    }

    @Test
    public void shouldReturnNotFoundCustomerWhenUpdate(){
        //TODO
    }

    @Test
    public void shouldReturnListOfShipments() throws Exception {

        Packages expectedShipment = testCustomerShipments.get(0);

        Optional<StatusPackage> expectedStatus = statusRepository.findStatusPackageByStatusId(expectedShipment.getStatusPackage().getStatusId());
        expectedStatus.ifPresent(statusPackage -> expectedShipment.getStatusPackage().setStatus_type(statusPackage.getStatus_type()));

        Optional<TypePackage> expectedType = typePackageRepository.findTypePackageByTypeId(expectedShipment.getTypePackage().getTypeId());
        expectedType.ifPresent(typePackage -> expectedShipment.getTypePackage().setType_name(typePackage.getType_name()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/customer/packages")
                        .param("username", "testusername")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()) // MockMvcResultMatchers
                        .andExpect(jsonPath("$[0].packageId").value(expectedShipment.getPackageId())) // MockMvcResultMatchers
                        .andExpect(jsonPath("$[0].name_package").value(expectedShipment.getName_package()))
                        .andExpect(jsonPath("$[0].statusPackage.status_type").value(expectedShipment.getStatusPackage().getStatus_type()))
                        .andExpect(jsonPath("$[0].typePackage.type_name").value(expectedShipment.getTypePackage().getType_name()))
                        .andExpect(jsonPath("$[0].customer.name").value(expectedShipment.getCustomer().getName()))
                        .andExpect(jsonPath("$[0].customer.lastName").value(expectedShipment.getCustomer().getLastName()))
                        .andExpect(jsonPath("$[0].customer.phone").value(expectedShipment.getCustomer().getPhone()))
                        .andExpect(jsonPath("$[0].date_register_package").value(expectedShipment.getDate_register_package()))
                        .andDo( result -> {

                            String content = result.getResponse().getContentAsString();

                            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

                            Object jsonObject = objectMapper.readValue(content, Object.class);
                            String prettyJson = objectMapper.writeValueAsString(jsonObject);

                            System.out.println("Response Content is : "+prettyJson);
                        });
    }

    @Test
    public void shouldReturnNotFoundShipments(){
        //TODO
    }

    @Test
    public void shouldReturnTrueWhenEmailExists() throws Exception {
        //Authentication testAuthentication = createTestAuthentication("testusername", "USER");

        mockMvc.perform(MockMvcRequestBuilders
                    .get("/customer")
                    .param("email", customer.getEmail())
             //       .header(HttpHeaders.AUTHORIZATION, "Bearer "+jwtTokenUtilTest.generateJwtToken(testAuthentication))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void shouldReturnFalseWhenEmailNotFound() throws Exception {
      //  Authentication testAuthentication = createTestAuthentication("testusername", "USER");

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/customer")
                        .param("email", "notfoundemail@gmail.com")
             //           .header(HttpHeaders.AUTHORIZATION, "Bearer "+jwtTokenUtilTest.generateJwtToken(testAuthentication))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    private void generateCustomerObject() {

        Customer customer = new Customer();
        customer.setName("Martin");
        customer.setLastName("Martinov");
        customer.setCity("Бургас");
        customer.setAddress("Levski Neighbourhood");
        customer.setEmail("testemail@gmail.com");
        customer.setPhone("0896422511");

        customer.setAccount(this.user_account);

        this.customer = customer;

        customerRepository.save(customer);
    }

    private CustomerDto generateCustomerDto(){
        Packages expectedShipment = testCustomerShipments.get(0);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setUserId(expectedShipment.getCustomer().getUserId());
        customerDto.setFirstName(expectedShipment.getCustomer().getName());
        customerDto.setLastName(expectedShipment.getCustomer().getLastName());
        customerDto.setEmail(expectedShipment.getCustomer().getEmail());
        customerDto.setPhone("0892876741"); // set new phone
        customerDto.setCity("Варна");
        customerDto.setAddress("Mladost st.Benkovska 5"); // set new address

        return customerDto;
    }

    private void getUser_account() {
        User_account user_account = new User_account();

        user_account.setUsername("testusername");
        user_account.setPassword("Testpassword=987654");


        List<Roles> roles = new ArrayList<>();

        Roles userRole = new Roles();

        userRole.setRole_description(Role.user);

        roles.add(userRole);

        user_account.setUserRoles(roles);

        this.user_account = user_account;
    }
    private void generateOffice(){

        City city = new City();
        //city.setCity_id(1L);
        city.setCity_name("Бургас");

        Office office = new Office();
        //office.setOffice_id(1L);
        office.setCity(city);
        office.setOffice_location("test location kv test");

        this.office = office;
    }

    private Authentication createTestAuthentication(String username, String role) {
        UserDetails userDetails = new UserAccountImpl(1L, username, "Testpassword=987654", Collections.singletonList(new SimpleGrantedAuthority(role)));
        return new UsernamePasswordAuthenticationToken(userDetails, "Testpassword=987654", userDetails.getAuthorities());
    }


    private void generateStatusPackage() {
        StatusPackage statusPackage = new StatusPackage();
        statusPackage.setStatusId(4L);
        statusPackage.setStatus_type("Izpratena");

        statusRepository.save(statusPackage);
    }

    private void generateTypePackage() {
        TypePackage typePackage = new TypePackage();

        typePackage.setTypeId(1L);
        typePackage.setType_name("Kolet");

        typePackageRepository.save(typePackage);
    }

}
