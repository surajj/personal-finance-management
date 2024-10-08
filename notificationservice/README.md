### Notification Service 
### Prerequisites
#### Before you start, ensure you have the following installed on your local machine:
- Java 17 or higher
- Maven (for building the project)
- Postman (for testing the APIs)

####  Follow the below steps to run notification service
- By Command line to run spring boot
  - mvn clean install spring-boot:run
- http://localhost:8084/


#### Setup and Running Services
1. Clone the Repository
   Clone the repository to your local machine.

```
git clone https://github.com/surajj/personal-finance-management.git
    cd personal-finance-management
```
2. Build the Project
   Navigate to each service directory and build the project using Maven:

```
cd notificationservices
mvn clean install
```

3. Start the Eureka Server
   Eureka Server handles service registration and discovery. Start it first:

```
cd eureka-server
mvn spring-boot:run
```
