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

<img src="https://user-images.githubusercontent.com/59741887/192603781-9c831775-dd8a-41b8-905c-3603d010accc.PNG" width="870" height="500"/>


####  Product Service
Contains Products details and customer reviews.  
Method	| Path	| Description	|
------------- | ------------------------- | -------------
GET	| /products	| Get all products details (filter products by /products?category={Product-Category})	
GET	| /product/:productId	| Get specified Product by Id	
GET	| /product/:productId/reviews	| Get reviews on specific product	
POST	| /product/:productId/review	| Rate and Review particular product	
DELETE | /product/:productId/review/:reviewId | Delete Review on product	
 

2. Product Service
4. Shopping Service
5. Order Service
4. Discovery Server
5. Spring Config Server
