package com.example.demo.services.impl;

import com.example.demo.models.entity.*;
import com.example.demo.private_lib.User;
import com.example.demo.repositories.CourierRepository;
import com.example.demo.repositories.PackageProblemRepository;
import com.example.demo.repositories.PackageRepository;
import com.example.demo.repositories.StatusRepository;
import com.example.demo.services.CourierService;
import com.example.demo.services.Impl.CourierServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class CourierServiceImplTest {

    private Courier testCourier;
    private List<Packages> testCourierShipments;
    private User_account testCourierAccount;
    private CourierService courierServiceTest;

    @Mock
    private CourierRepository mockCourierRepository;

    @Mock
    private PackageRepository mockPackageRepository;

    @Mock
    private StatusRepository mockStatusRepository;

    @Mock
    private PackageProblemRepository mockPackageProblemRepository;

    @Mock
    private ApplicationEventPublisher mockApplicationEventPublisher;

    @BeforeEach
    void setup(){
        courierServiceTest = new CourierServiceImpl(mockCourierRepository,mockPackageRepository,mockStatusRepository,
                                                    mockPackageProblemRepository, mockApplicationEventPublisher);

        testCourier = new Courier();
        testCourier.setCourier_id(1L);
        testCourier.setCourier_first_name("Kalin");
        testCourier.setCourier_last_name("Kalinov");
        testCourier.setPhone("0895225512");
        testCourier.setCourier_city_name("Бургас");

        testCourierAccount = new User_account();

        testCourierAccount.setUser_account_id(1L);
        testCourierAccount.setUsername("testcourier");
        testCourierAccount.setPassword("testpassword");

        testCourier.setAccount(testCourierAccount);

        testCourierShipments = new ArrayList<>();

        StatusPackage problemStatusPackage = this.getProblemStatusPackage();

        StatusPackage deliveredStatusPackage = this.getDeliveredStatusPackage();

        TypePackage typePackage = this.getTypePackage();

        setShipment(typePackage, problemStatusPackage, 1L,"Test shipment1", 100.0);

        setShipment(typePackage, deliveredStatusPackage, 2L,"Test shipment2", 200.0);
    }

    @Test
    public void shouldReturnListOfProblemShipments() throws Exception {

        List<Packages> problemShipments = testCourierShipments.stream()
                .filter(shipment -> shipment.getStatusPackage().getStatusId() == 2)
                .collect(Collectors.toList());

        List<PackageProblem> expectedPackageProblems = this.generatePackagesProblemShipments(problemShipments);

        Mockito.when(mockPackageProblemRepository.findProblemCourierPackagesByUsername(testCourier.getAccount().getUsername()))
                .thenReturn(expectedPackageProblems);

        List<PackageProblem> actualCourierProblemPackages = courierServiceTest.getCourierProblemPackages(testCourier.getAccount().getUsername());

        Set<Long> expectedShipmentIds = expectedPackageProblems.stream().map(packageProblem -> packageProblem.getPackages_problem().getPackageId()).collect(Collectors.toSet());

        Set<Long> actualShipmentIds = actualCourierProblemPackages.stream().map(packageProblem -> packageProblem.getPackages_problem().getPackageId()).collect(Collectors.toSet());

        boolean isEquals = expectedShipmentIds.equals(actualShipmentIds);

        Assertions.assertTrue(isEquals);
    }

    @Test
    public void shouldReturnListOfDeliveredShipments() throws Exception {
        List<Packages> expectedDeliveredShipments = testCourierShipments.stream()
                .filter(packages -> packages.getStatusPackage().getStatusId() == 4)
                .collect(Collectors.toList());

        Mockito.when(mockPackageRepository.findDeliveredCourierPackagesByUsername(testCourier.getAccount().getUsername()))
                .thenReturn(expectedDeliveredShipments);

        List<Packages> actualDeliveredShipments = courierServiceTest.getDeliveredPackages(testCourier.getAccount().getUsername());

        Set<Long> actualDeliveredShipmentsIds = actualDeliveredShipments.stream().map(Packages::getPackageId).collect(Collectors.toSet());

        Set<Long> expectedDeliveredShipmentsIds = expectedDeliveredShipments.stream().map(Packages::getPackageId).collect(Collectors.toSet());

        boolean isEquals = actualDeliveredShipmentsIds.equals(expectedDeliveredShipmentsIds);

        Assertions.assertTrue(isEquals);
    }

    @Test
    public void shouldReturnListOfAllCouriers(){
        List<Courier> courierList = new ArrayList<>();

        Courier courier2 = new Courier();
        courier2.setCourier_id(2L);
        courier2.setCourier_first_name("Spas");
        courier2.setCourier_last_name("Spasov");
        courier2.setPhone("0894122129");
        courier2.setCourier_city_name("Бургас");

        testCourierAccount = new User_account();

        testCourierAccount.setUser_account_id(2L);
        testCourierAccount.setUsername("testcourier2");
        testCourierAccount.setPassword("testpassword2");

        courier2.setAccount(testCourierAccount);

        courierList.add(testCourier);
        courierList.add(courier2);

        Mockito.when(mockCourierRepository.findAll()).thenReturn(courierList);

        List<Courier> actualListCouriers = courierServiceTest.getAllCouriers();

        Set<String> actualCouriersPhones = actualListCouriers.stream().map(Courier::getPhone).collect(Collectors.toSet());

        Set<String> expectedCouriersPhones = courierList.stream().map(Courier::getPhone).collect(Collectors.toSet());

        boolean isEquals = expectedCouriersPhones.equals(actualCouriersPhones);

        Assertions.assertTrue(isEquals);
    }

    private List<PackageProblem> generatePackagesProblemShipments(List<Packages> problemShipments) {

        List<PackageProblem> packageProblems = new ArrayList<>();

        for(Packages shipment : problemShipments){

            PackageProblem problemShipment = new PackageProblem();

            problemShipment.setPackage_problem_id(1L);
            problemShipment.setPackages_problem(shipment);
            problemShipment.setMessage("Fake shipment");

            packageProblems.add(problemShipment);
        }
        return packageProblems;
    }

    private void setShipment(TypePackage typePackage, StatusPackage statusPackage, Long shipmentId, String nameShipment, double priceShipment) {
        Packages shipment = new Packages();

        shipment.setPackageId(shipmentId);
        shipment.setName_package(nameShipment);
        shipment.setDate_register_package(java.sql.Date.valueOf(LocalDate.now()));
        shipment.setPackage_price(priceShipment);
        shipment.setTotal_cost(priceShipment);
        shipment.setTypePackage(typePackage);
        shipment.setStatusPackage(statusPackage);

        shipment.setCourier(testCourier);

        Customer customer = this.generateCustomer();

        shipment.setCustomer(customer);

        testCourierShipments.add(shipment);
    }

    private StatusPackage getProblemStatusPackage() {
        StatusPackage statusPackage = new StatusPackage();

        statusPackage.setStatusId(2L);
        statusPackage.setStatus_type("Проблем с пратката");
        return statusPackage;
    }

    private StatusPackage getDeliveredStatusPackage() {
        StatusPackage statusPackage = new StatusPackage();

        statusPackage.setStatusId(4L);
        statusPackage.setStatus_type("Доставена");
        return statusPackage;
    }

    private TypePackage getTypePackage() {
        TypePackage typePackage = new TypePackage();

        typePackage.setTypeId(1L);
        typePackage.setType_name("Колет");
        return typePackage;
    }

    private Customer generateCustomer(){

        Customer testCustomer = new Customer();

        testCustomer.setUserId(1L);
        testCustomer.setName("Martin");
        testCustomer.setLastName("Martinov");
        testCustomer.setCity("Varna");
        testCustomer.setAddress("Levski Neighbourhood");
        testCustomer.setEmail("testemail@gmail.com");
        testCustomer.setPhone("0896422511");

        return testCustomer;
    }
}
