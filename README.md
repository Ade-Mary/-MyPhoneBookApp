# Spring Boot Backend API Documentation

This project provides a **REST API** for managing contacts, built with **Spring Boot**. The backend handles contact CRUD operations, contact grouping, advanced search, favorites, and CSV import/export functionality. The application interacts with either a **PostgreSQL** or **MySQL** database, depending on your configuration. The API integrates smoothly with the **Angular** frontend for a seamless user experience.

---

##  Prerequisites

Before you begin, ensure you have the following prerequisites installed:

- Java 17
- Spring 6
- Spring Boot 3
- Spring Security
- PostgreSQL Database

## 🛠️ Dependencies

The following dependencies are required for the project:

- Spring Web
- Spring Data JPA
- OpenCSV
- Project Lombok
- Spring Validation
- Postgres

## ⚙️ Configure the application

The configuration for this API is stored in the `application.properties` file.

### 🗄️ Configure Database Connection

Update the database configuration in the `application.properties` file:

```properties
spring.datasource.url=your-database-url
spring.datasource.username=root
spring.datasource.password=your-database-password
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect



## 📚 Postman API Documentation
This project comes with a comprehensive Postman API documentation which can be accessed [here]

https://documenter.getpostman.com/view/36483828/2sAYXBFJzP
