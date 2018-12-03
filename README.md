# users-backoffice-java
Spring-boot java based application with bootstrap to manage users.

## How to start

On the project's root, run:
```shell
mvn clean package
mvn spring-boot:run
```

The package is necessary to compile and generate the required code by mappers ([mapstructs](http://mapstruct.org/) library).
The application also use [Project-Lombok](https://projectlombok.org/) to generate repetitive code (as Getters and Setters, Constructors, Builders, ...).

## How to use

The project has a default user with credentials admin:test
Usefull endpoints are:
 * http://localhost:8080/
 * http://localhost:8080/swagger-ui.html

## About
The application uses an in-memory MongoDB so, no mongodb is required to be configured.
All users stores a plain password (without encode it) in order to test all app functionallity (on a real-use application this must be removed).
Also, it have a Credentials endpoint to obtain all username and passwords in order to log-in with a desired user.

