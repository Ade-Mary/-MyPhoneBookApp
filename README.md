Spring Boot Backend API Documentation

This project provides a REST API for managing contacts, built with Spring Boot. The backend handles contact CRUD operations, contact grouping, advanced search, favorites, and CSV import/export functionality. The application interacts with either a PostgreSQL or MySQL database, depending on your configuration. The API integrates smoothly with the Angular frontend for seamless user experience.

Prerequisites
Java 17
Spring 6
Spring Boot 3
Spring Security
PostgreSQL Database
Dependencies
Spring Web
Spring Data JPA
Project Lombok
Spring Validation
MySQL


Configure the application
The configuration for this API is stored in the application.properties file.

Configure Database Connection
Update the database configuration in the application.properties file:
spring.datasource.url=your-database-url
spring.datasource.username=root
spring.datasource.password=your-database-password
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

Postman API Documentation
This project comes with a comprehensive Postman API documentation which can be accessed here:

View Postman API Documentation

