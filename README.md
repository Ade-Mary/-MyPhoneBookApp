<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Backend Documentation</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

    <div class="container mt-5">
        <h1 class="text-center">Spring Boot Backend Documentation</h1>

        <section class="mt-4">
            <h2>Project Overview</h2>
            <p>The Spring Boot backend of this project offers a REST API for contact management. For data storage, the backend interacts with PostgreSQL or MySQL, allowing for CSV import/export, contact grouping, advanced search, favorites, and CRUD operations. It handles contact management requests from the Angular application and guarantees smooth connection with the frontend.</p>
        </section>

        <section class="mt-4">
            <h2>Prerequisites</h2>
            <p>Before you begin, ensure you have the following prerequisites installed:</p>
            <ul>
                <li>Java 17</li>
                <li>Spring 6</li>
                <li>Spring Boot 3</li>
                <li>Spring Security</li>
                <li>PostgreSQL Database</li>
            </ul>
        </section>

        <section class="mt-4">
            <h2>Dependencies</h2>
            <p>The following dependencies are required for the project:</p>
            <ul>
                <li>Spring Web</li>
                <li>Spring Data JPA</li>
                <li>Project Lombok</li>
                <li>Spring Validation</li>
                <li>MySQL</li>
            </ul>
        </section>

        <section class="mt-4">
            <h2>Configure the Application</h2>
            <p>The configuration for this API is stored in the <code>application.properties</code> file.</p>
            <pre>
                spring.datasource.url=your-database-url
                spring.datasource.username=root
                spring.datasource.password=your-database-password
                spring.jpa.hibernate.ddl-auto=update
                spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
                spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
            </pre>
        </section>

        <section class="mt-4">
            <h2>Postman API Documentation</h2>
            <p>This project comes with a comprehensive Postman API documentation which can be accessed here:</p>
            <a href="https://documenter.getpostman.com/view/36483828/2sAYXBFJzP" target="_blank" class="btn btn-primary">View Postman API Documentation</a>
        </section>
    </div>

</body>
</html>
