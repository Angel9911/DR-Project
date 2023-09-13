# Courier service system - Web Application - Spring Boot - Angular

## :pencil: Project Description
It's the repository where the project files are stored. There are code from FrontEnd framework(Angular) and BackEnd(Spring Boot). Also there is a ER Diagram which describes the tables where the information are stored and shows the relationships between objects.
The server and the client communicate by using a rest api. The client send the request to the specified end point, the server processes the submitted information and return a http response with object or collection of objects to the client.
All server resources are forbidden to the unauthorized users. When the user logs in, the server generates and return "bearer" token to the client which is stored in the local storage. This token is active for 30 minutes.

## :floppy_disk: Database Diagram
![DataBaseDiagram](https://github.com/Angel9911/DR-Project/blob/main/er_diagram.png)

## :hammer: Used technologies
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Java](https://www.w3schools.com/java/)
* TypeScript
* [Angular](https://angular.io/)
* [Bootstrap](https://github.com/twbs/bootstrap)
* [jUnit](https://junit.org/junit5/)
* [PostgreSQL](https://www.postgresql.org/)
* HTML & CSS
* SOLID  PRINCIPLES AND DESIGN PATTERNS

## Images

#### • Customer view

* SignUp/SignIn Pages for the customers
![RegisterViewModel](https://github.com/Angel9911/DR-Project/blob/main/Using_System_Pictures/reg_tmpl.png)
![LoginViewPage](https://github.com/Angel9911/DR-Project/blob/main/Using_System_Pictures/login_tmpl.png)

* Packages Page - Here customers can see information about their all shipments(delivered, shipped, as well as problem shipments).
![CustomerShipmentsView](https://github.com/Angel9911/DR-Project/blob/main/Using_System_Pictures/customer_packages_1.png)

* Filter shipments by shipment name and price
![FilterShipmentsByPrice](https://github.com/Angel9911/DR-Project/blob/main/Using_System_Pictures/customer_packages_filter_name_package.png)
![FilterShipmentsByPrice](https://github.com/Angel9911/DR-Project/blob/main/Using_System_Pictures/customer_packages_filter_price_package.png)

#### • Courier view

* List of shipments that the courier must deliver(filtered by customer phone)
![FilterCourierShipmentsByPhone](https://github.com/Angel9911/DR-Project/blob/main/Using_System_Pictures/courier_list_package_search_phone.png)

* Filtered by shipment name
![FilterCourierShipmentsByName](https://github.com/Angel9911/DR-Project/blob/main/Using_System_Pictures/courier_list_package_search_package_name.png)

* Problem Shipments
![ProblemShipments](https://github.com/Angel9911/DR-Project/blob/main/Using_System_Pictures/courier_packages_problem.png)

* Register a problem shipment
![RegisterProblemShipment](https://github.com/Angel9911/DR-Project/blob/main/Using_System_Pictures/courier_package_problem.png)

* Delivered shipments
![DeliveredShipments](https://github.com/Angel9911/DR-Project/blob/main/Using_System_Pictures/courier_list_delivered_package.png)

* Register a successfully delivered shipment
![RegisterSuccessShipment](https://github.com/Angel9911/DR-Project/blob/main/Using_System_Pictures/courier_delivered_package_success.png)

#### • Administrator view

* Register a shipment in the system
![RegisterShipment](https://github.com/Angel9911/DR-Project/blob/main/Using_System_Pictures/register_package.png)

* All registered customers in the system(Filtered by phone)
![AllCustomers](https://github.com/Angel9911/DR-Project/blob/main/Using_System_Pictures/administrator_list_customers_tmpl.png)
![AllCustomersFilteredByPhone](https://github.com/Angel9911/DR-Project/blob/main/Using_System_Pictures/administrator_list_customers_search_tmpl.png)

* All registered couriers in the system(Filtered by phone)
![AllCouriers](https://github.com/Angel9911/DR-Project/blob/main/Using_System_Pictures/administrator_list_couriers_tmpl.png)
![AllCouriersFilteredByPhone](https://github.com/Angel9911/DR-Project/blob/main/Using_System_Pictures/administrator_list_couriers_search_tmpl.png)

## Author
Angel Dimitrov
<br />
[LinkedIn](https://www.linkedin.com/in/angel-dimitrov-70331b215/)
