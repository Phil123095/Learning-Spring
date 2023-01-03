# Read Me // General Notes & Learnings
The following was discovered as part of building this project:

* The JVM level was changed from '11' to '17', review the [JDK Version Range](https://github.com/spring-projects/spring-framework/wiki/Spring-Framework-Versions#jdk-version-range) on the wiki for more details.

# Typical Spring Boot Architecture [Source](https://www.javatpoint.com/spring-boot-architecture)

## Four Layers
### 1 - Presentation Layer
* = Handles HTTP requests, translates JSON parameter to object, etc. Transfers request to business layer.
* Requests made by client (e.g. PUT, GET, etc.) will go to the CONTROLLER (class denoted by @Controller / @RestController). 
* Controller will map request and handle it (ex. @GetMapping("/")). 
* It then calls SERVICE LOGIC if required.

### 2 - Business Layer
* = Handles all the BUSINESS LOGIC. Consists of **service classes** and uses services provided by Data Access Layers. 
* Also performs Auth and Validation.
* Service Logic (@Service) is called by Controller, and perform the logic on the data. Data is mapped to JPA* with model classes.

### 3 - Persistence Layer
* = Contains all the storage logic. Translates business objects from and to DB rows. (ORM)

### 4 - Database Layer
* = Where CRUD operations are performed. 

![architecture diagram](https://static.javatpoint.com/springboot/images/spring-boot-architecture2.png)

#### * What is JPA
* = Jakarta/Java Persistence API. Lets you avoid thinking "relationally" (as opposed to JDBC, where you need to map code to relational tables and back again)
* Enables ORM = Object-Relational Mapping. ORM Layer = Responsible for mapping conversion of SW objects to interact w/ tables & cols in relational DB.
* Defaults: Name of object = Name of table, Object Properties/Fields = Columns, 1 Object = 1 Row.