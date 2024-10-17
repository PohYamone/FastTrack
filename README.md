
# CSCI318 Group Project Part C

## Overview

This project is an e-commerce platform developed using a microservices architecture. The system consists of various services (Order, Payment, Shipping, Inventory, Analytics) built using **Spring Boot**, and they communicate asynchronously through **Apache Kafka**. The project aims to provide a scalable and flexible platform capable of handling real-time operations, including order management, stock tracking, payment processing, and shipping updates.

## Prerequisites

Before running the project, ensure you have the following components installed:

- **JDK 21**: You can verify the installation by running:
  ```bash
  java --version
  ```
- **Maven**: Maven is used for building the project and managing dependencies. Verify it with:
  ```bash
  mvn --version
  ```

## Apache Kafka Setup
This Spring Boot project uses Apache Kafka as a messaging platform.
To run this project, you need to set up Kafka first.

#### Linux and MacOS
Download a **binary package** of Apache Kafka (e.g., `kafka_2.13-3.7.0.tgz`) from
[https://kafka.apache.org/downloads](https://kafka.apache.org/downloads)
and upzip it.
In the Terminal, `cd` to the unzip folder, and start Kakfa with the following commands (each in a separate Terminal session):
```bash
./bin/zookeeper-server-start.sh ./config/zookeeper.properties
```
```bash
./bin/kafka-server-start.sh ./config/server.properties
```

#### Windows
Download a **binary package** of Apache Kafka (e.g., `kafka_2.13-3.7.0.tgz`) from
[https://kafka.apache.org/downloads](https://kafka.apache.org/downloads)
and unzip it to a directory, e.g., `C:\kafka`&mdash;Windows does not like a complex path name (!).

<!--
In the configuration file `C:\kafka\config\zookeeper.properties`, comment out the line `"dataDir=/tmp/zookeeper"`. In `C:\kafka\config\server.properties`, change the line `"log.dirs=/tmp/kafka-logs"` to `"log.dirs=.kafka-logs"`.
-->

Use the following two commands in the Windows CMD (one in each window) to start Kafka:
```bash
C:\kafka\bin\windows\zookeeper-server-start.bat C:\kafka\config\zookeeper.properties
```
```bash
C:\kafka\bin\windows\kafka-server-start.bat C:\kafka\config\server.properties
```

- **IDE**: Use any Java IDE such as **Visual Studio Code**, **IntelliJ IDEA**, or **Eclipse**. The following instructions use Visual Studio Code as an example.

## Running the Application

### Method 1: Using Visual Studio Code

1. **Clone the Project**:  
   Clone the project repository to your local machine.

2. **Open the Project in VSCode**:  
   Navigate to the project folder and open it in Visual Studio Code.

3. **Select Run and Debug**:  
   On the left-hand side of VSCode, go to the **Run and Debug** tab.

4. **Run All Services**:  
   Select the option to **Run all services** from the drop-down and click the play button.


### Method 2: Manually Running Each Service

1. **Clone the Project**:  
   Clone the project repository to your local machine.

2. **Open the Project in Your IDE**:  
   Open the project folder in your IDE.

3. **Build the Project**:  
   In the terminal, navigate to the project root (where the `pom.xml` is located) and run:
   ```bash
   mvn clean install
   ```

4. **Run Each Service**:  
   Open separate terminals for each service and run the following commands:

   ```bash
   mvn spring-boot:run -pl inventory-service
   ```

   ```bash
   mvn spring-boot:run -pl shipping-service
   ```

   ```bash
   mvn spring-boot:run -pl cart-service
   ```

   ```bash
   mvn spring-boot:run -pl user-service
   ```

   ```bash
   mvn spring-boot:run -pl payment-service
   ```

   ```bash
   mvn spring-boot:run -pl order-service
   ```

   ```bash
   mvn spring-boot:run -pl analytics-service
   ```

   ```bash
   mvn spring-boot:run -pl product-service
   ```


## RESTful API Requests

### User Service

- **Register a User**:
  ```bash
  curl -X POST -H "Content-Type:application/json" -d '{"name":"Poh", "email":"poh@uowmail.edu.au", "password":"password", "address": {"city":"Wollongong", "state":"NSW", "country":"Australia"}, "age":25, "gender":"female"}' http://localhost:8080/api/users
  ```

- **Get All Users**:
  ```bash
  curl -X GET http://localhost:8080/api/users
  ```

- **Get User by ID**:
  ```bash
  curl -X GET http://localhost:8080/api/users/1
  ```

- **User Login**:
  ```bash
  curl -X POST -H "Content-Type:application/json" -d '{"email":"poh@uowmail.edu.au", "password":"password"}' http://localhost:8080/api/users/login
  ```

### Product Service

- **Create a Product**:
  ```bash
  curl -X POST -H "Content-Type:application/json" -d '{"name":"XPS", "category":"Laptop", "price":1999, "weight":0.8}' http://localhost:8081/api/products
  ```

- **Get All Products**:
  ```bash
  curl -X GET http://localhost:8081/api/products
  ```

- **Get Product by ID**:
  ```bash
  curl -X GET http://localhost:8081/api/products/3
  ```

### Cart Service

- **Create a Cart for a User**:
  ```bash
  curl -X POST http://localhost:8083/api/carts/user/1
  ```

- **Get All Carts for a User**:
  ```bash
  curl -X GET http://localhost:8083/api/carts/user/1
  ```

- **Add Product to a Cart**:
  ```bash
  curl -X PUT -H "Content-Type:application/json" -d '{"productId":1, "quantity":5}' http://localhost:8083/api/carts/1/products
  ```

- **Remove Product from Cart**:
  ```bash
  curl -X DELETE http://localhost:8083/api/carts/1/products/1
  ```

### Order Service

- **Create an Order**:
  ```bash
  curl -X POST -H "Content-Type:application/json" http://localhost:9090/api/orders/1
  ```

- **Get All Orders**:
  ```bash
  curl -X GET http://localhost:9090/api/orders
  ```

- **Get Order by ID**:
  ```bash
  curl -X GET http://localhost:9090/api/orders/1
  ```

### Payment Service

- **Create a Payment**:
  ```bash
  curl -X POST -H "Content-Type:application/json" -d '{"orderId": 1, "customerId": 1, "amount": 100}' http://localhost:8087/api/payments
  ```

- **Get Payment for an Order**:
  ```bash
  curl -X GET http://localhost:8087/api/payments/orders/1
  ```

### Shipping Service

- **Update Shipping**:
  ```bash
  curl -X PATCH "http://localhost:8089/api/shipping/1/update"
  ```

- **Get All Shipments**:
  ```bash
  curl -X GET "http://localhost:8089/api/shipping"
  ```

### Analytics Service

- **Get All Active Orders**:
  ```bash
  curl -X GET "http://localhost:8096/orders/active"
  ```

- **Get Active Order Count for a User**:
  ```bash
  curl -X GET "http://localhost:8096/orders/active/1"
  ```

- **Get All Stock Levels**:
  ```bash
  curl -X GET "http://localhost:8096/stock/levels"
  ```

- **Get Stock Level for a Product**:
  ```bash
  curl -X GET "http://localhost:8096/stock/levels/1"
  ```

## Real-Time Analysis with Kafka Streams

The system uses **Kafka Streams API** to implement real-time stream processing for critical operations such as:

1. **Real-Time Stock Level Tracking**:  
   Monitors stock levels and triggers alerts when stock falls below 5.

2. **Real-Time Order Tracking**:  
   Tracks orders in real-time and classifies them as active or inactive based on status.

By leveraging Kafka Streams, the platform provides immediate feedback and processing for stock and order data.

## Conclusion

This README provides instructions to set up, run, and test the microservices for our e-commerce platform. Follow the provided `curl` commands to interact with each microservice.
