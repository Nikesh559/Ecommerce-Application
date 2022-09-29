# Ecommerce-Website
Microservice-Based Ecommerce Application, with functionality to search products, add to cart, checkout, and track the shipment.
# Table of Contents
* Technologies used 
* Project Overview
* Project Architecture
* 
* 


# Technologies used
* Java
* Spring Boot
* Spring Data JPA
* Netflix Eureka
* Resilence4j 
* Spring Config Server


# Project Architecture

![Facebook API drawio(1)](https://user-images.githubusercontent.com/59741887/193088250-e46a017b-0670-4a2e-bb6f-5d44506ec3f6.png)

###  Product Service
Contains Products details and customer reviews and rating.  
Method	| Path	| Description	|
------------- | ------------------------- | -------------
GET	| /products	| Get all products details 	
GET	| /product/{productId}	| Get specified Product by Id	
GET	| /product/{productId}/reviews	| Get reviews on specific product	
POST	| /product/{productId}/review	| Rate and Review particular product	
DELETE | /product/{productId}/review/{reviewId} | Delete Review on product	
 
### Shopping Cart Service
Customers can add/delete products to/from there cart and can checkout cart to place order.

Method	| Path	| Description	|
------------- | ------------------------- | -------------
GET	| /customers/{customerId}/cart-items	| Get products added to cart 
POST	| /customers/{customerId}/cart-item/{productId}	| Add product to cart	
DELETE	| /customers/{customerId}/cart-item/{productId}	| Delete product from cart	
POST	| /customers/{customerId}/cart-checkout	| Checkout Cart	

### Order Service
Customers can fetch order details to track the shipment and can cancel order if it is not delivered. 

Method	| Path	| Description	|
------------- | ------------------------- | -------------
GET	| /customers/{customerId}/orders	| Get all orders placed by customer id
GET	| /customers/{customerId}/order/{orderId}	| Fetch specific order	
PUT	| /customers/{customerId}/order/{orderId}	| Cancel order 	

### Discovery Server

Service Discovery allows automatic detection of the network locations for all registered services. These service might have dynamically assigned addresses. 
In this project, <b>Netflix Eureka</b> is used. It uses client-service discovery pattern, where clients fetch the registry from discovery server for determining the location available services and load balancing between them. 

Eureka Dashboard provides information of running services and no. of instance: `http://localhost:8761`

![Eureka-Dashboard](https://user-images.githubusercontent.com/59741887/193081538-c5507ade-d0e6-4541-a3ad-b946dd0ae7d8.PNG)


### Spring Config Server

For Externalizing the configuration and to provide centralized configuration for distributed system, <b> Spring Cloud Config Server </b> is used that fetches the configuration from git repository. All services fetch the databases configurations from spring config server. 
In this project, a local git repository is created for each service as below.

![Config-repo](https://user-images.githubusercontent.com/59741887/193084723-92d403ff-1ca4-4b9f-a0ec-21a6edb65277.PNG)

### Resilience4j

To build fault tolerant and resilient microservice, <b> Circuit Breaker pattern</b> is used which gives control over network failure or over latency while communicating with other microservices. It prevents from cascading failure.

CircuitBreaker mainly operates in three modes : <b> CLOSED </b>,<b> OPEN </b>,<b> HALF_OPEN </b>.
One can monitor, the state of circuit breaker from actuator health endpoint: `http://localhost/actuator/health`.

<img src="https://user-images.githubusercontent.com/59741887/193087145-b03aa191-b9ef-4e39-bc3d-cd3115989fc7.PNG" width="500" height="500"/>
