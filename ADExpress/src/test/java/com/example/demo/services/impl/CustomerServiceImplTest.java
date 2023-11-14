package com.example.demo.services.impl;

import com.example.demo.models.entity.*;
import com.example.demo.models.views.CustomerView;
import com.example.demo.models.views.User_accountView;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.PackageRepository;
import com.example.demo.services.CustomerService;
import com.example.demo.services.Impl.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest{

    private Customer testCustomer;
    private User_account testUserAccount;
    private List<Packages> testCustomerShipments;
    private CustomerService customerServiceTest;

    @Mock
    private CustomerRepository mockCustomerRepository;
    @Mock
    private CityRepository mockCityRepository;
    @Mock
    private PackageRepository mockPackageRepository;


    @BeforeEach
    void setup(){

        customerServiceTest = new CustomerServiceImpl(mockCustomerRepository,mockCityRepository,mockPackageRepository);

        testCustomer = new Customer();
        testCustomer.setUserId(1L);
        testCustomer.setName("Martin");
        testCustomer.setLastName("Martinov");
        testCustomer.setCity("Varna");
        testCustomer.setAddress("Levski Neighbourhood");
        testCustomer.setEmail("testemail@gmail.com");
        testCustomer.setPhone("0896422511");

        testUserAccount = new User_account();

        testUserAccount.setUser_account_id(1L);
        testUserAccount.setUsername("testusername");
        testUserAccount.setPassword("testpassword");

        testCustomer.setAccount(testUserAccount);

        testCustomerShipments = new ArrayList<>();

        TypePackage typePackage = getTypePackage();

        StatusPackage statusPackage = getStatusPackage();

        setShipment(typePackage, statusPackage, 1L,"Test shipment1", 100.0);

        setShipment(typePackage, statusPackage, 2L,"Test shipment2", 200.0);
    }

    @Test
    public void shouldReturnListOfCustomers() throws Exception {

        Customer customer2 = new Customer();
        customer2.setUserId(2L);
        customer2.setName("Test");
        customer2.setLastName("Test");
        customer2.setCity("Sofia");
        customer2.setAddress("Levski Neighbourhood");
        customer2.setEmail("testemail2@gmail.com");
        customer2.setPhone("0891225218");

        List<Customer> customerList = new ArrayList<>();
        customerList.add(testCustomer);
        customerList.add(customer2);

        Mockito.when(mockCustomerRepository.findAll()).thenReturn(customerList);

        List<Customer> actualCustomers = customerServiceTest.getAllCustomers();
        Assertions.assertEquals(customerList.size(),actualCustomers.size());

        Set<String> actualCustomersPhone = actualCustomers.stream().map(Customer::getPhone).collect(Collectors.toSet());
        Set<String> expectedCustomersPhone = customerList.stream().map(Customer::getPhone).collect(Collectors.toSet());

        boolean isCustomerPhoneEquals = expectedCustomersPhone.equals(actualCustomersPhone);

        Assertions.assertTrue(isCustomerPhoneEquals);
    }

    @Test
    public void shouldReturnListOfCustomerShipments() throws Exception {

        Mockito.when(mockPackageRepository.findPackagesByUser_accountUsername(testCustomer.getAccount().getUsername())).thenReturn(testCustomerShipments);

        List<Packages> actualCustomerShipments = customerServiceTest.getAllPackages(testCustomer.getAccount().getUsername());

        Assertions.assertEquals(testCustomerShipments.size(),actualCustomerShipments.size());

        Set<Long> expectedIds = testCustomerShipments.stream().map(Packages::getPackageId).collect(Collectors.toSet());
        Set<Long> actualIds = actualCustomerShipments.stream().map(Packages::getPackageId).collect(Collectors.toSet());

        boolean isEquals = expectedIds.equals(actualIds);

        Assertions.assertTrue(isEquals);
    }

    @Test
    public void shouldCustomerFoundWhenLogsIn() throws ValidationException {

        //arrange
        Mockito.when(mockCustomerRepository.findByUserId(testCustomer.getUserId())).thenReturn(createExpectedCustomerView());

        // actual
        CustomerView actualCustomer = customerServiceTest.Login(testCustomer.getUserId());

        Assertions.assertEquals(testCustomer.getName(),actualCustomer.getName());
        Assertions.assertEquals(testCustomer.getLastName(),actualCustomer.getLastName());
        Assertions.assertEquals(testCustomer.getCity(),actualCustomer.getCity());
        Assertions.assertEquals(testCustomer.getAddress(),actualCustomer.getAddress());
        Assertions.assertEquals(testCustomer.getEmail(),actualCustomer.getEmail());
        Assertions.assertEquals(testCustomer.getPhone(),actualCustomer.getPhone());
        Assertions.assertEquals(testCustomer.getAccount().getUsername(),actualCustomer.getAccount().getUsername());
    }

    @Test
    public void isOwnerCustomer_WhenIdIsValid_ReturnTrue(){

        Mockito.when(mockCustomerRepository.findById(testCustomer.getUserId())).thenReturn(Optional.of(testCustomer));

        boolean actualResult = customerServiceTest.isOwner("testusername", 1L);

        Assertions.assertTrue(actualResult);
    }

    @Test
    public void shouldReturnFalseWhenIdIsNotValid(){

        Mockito.when(mockCustomerRepository.findById(2L)).thenReturn(Optional.empty());

        boolean actualResult = customerServiceTest.isOwner("testusername", 2L);

        Assertions.assertFalse(actualResult);
    }

    @Test
    public void shouldReturnNullWhenEmailNotFound(){
        Optional<Customer> actualCustomer = customerServiceTest.IsEmailExist("wrongemail@gmail.com");

        Assertions.assertTrue(actualCustomer.isEmpty());
    }

    @Test
    public void shouldReturnCustomerWhenEmailExists(){

        //arrange
        Mockito.when(mockCustomerRepository.findByEmail(testCustomer.getEmail())).thenReturn(Optional.of(testCustomer));

        //actual result
        Optional<Customer> optionalCustomer = customerServiceTest.IsEmailExist(testCustomer.getEmail());

        //expected result

        // when the result will be null or object, firstly check if the result is not null
        Assertions.assertNotNull(optionalCustomer);
        Assertions.assertTrue(optionalCustomer.isPresent());

        Customer actualCustomer = optionalCustomer.get();

        // then check actual result with expected
        Assertions.assertEquals(actualCustomer.getEmail(),testCustomer.getEmail());
        Assertions.assertEquals(actualCustomer.getName(),testCustomer.getName());
        Assertions.assertEquals(actualCustomer.getPhone(),testCustomer.getPhone());
        Assertions.assertEquals(actualCustomer.getAccount().getUsername(),testCustomer.getAccount().getUsername());
    }

    private void setShipment(TypePackage typePackage, StatusPackage statusPackage, Long shipmentId, String nameShipment, double priceShipment) {
        Packages shipment = new Packages();

        shipment.setPackageId(shipmentId);
        shipment.setName_package(nameShipment);
        shipment.setDate_register_package(java.sql.Date.valueOf(LocalDate.now()));
        shipment.setTotal_cost(priceShipment);
        shipment.setTypePackage(typePackage);
        shipment.setStatusPackage(statusPackage);

        shipment.setCustomer(testCustomer);

        testCustomerShipments.add(shipment);
    }

    private StatusPackage getStatusPackage() {
        StatusPackage statusPackage = new StatusPackage();

        statusPackage.setStatusId(4L);
        statusPackage.setStatus_type("Изпратена");
        return statusPackage;
    }

    private TypePackage getTypePackage() {
        TypePackage typePackage = new TypePackage();

        typePackage.setTypeId(1L);
        typePackage.setType_name("Колет");
        return typePackage;
    }
    private CustomerView createExpectedCustomerView(){
        return new CustomerView() {
            @Override
            public String getName() {
                return testCustomer.getName();
            }

            @Override
            public String getLastName() {
                return testCustomer.getLastName();
            }

            @Override
            public String getCity() {
                return testCustomer.getCity();
            }

            @Override
            public String getEmail() {
                return testCustomer.getEmail();
            }

            @Override
            public String getAddress() {
                return testCustomer.getAddress();
            }

            @Override
            public String getPhone() {
                return testCustomer.getPhone();
            }

            @Override
            public User_accountView getAccount() {
                return createExpectedUserAccountView();
            }
        };
    }
    private User_accountView createExpectedUserAccountView(){
        return new User_accountView() {
            @Override
            public String getUsername() {
                return testCustomer.getAccount().getUsername();
            }
        };
    }
}
