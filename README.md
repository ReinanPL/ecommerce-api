<h1>E-Commerce</h1>

<p>
  <a href="https://www.linkedin.com/in/reinancarvalho323/" target="_blank">
  <img src="https://img.shields.io/static/v1?label=Linkedin&message=@reinancarvalho&color=8257E5&labelColor=000000" alt="@reinancarvalho" />
</a>
  <img src="https://img.shields.io/static/v1?label=Tipo&message=Challenge&color=8257E5&labelColor=000000" alt="Challenge" />
</p>

<p>
  <strong>This API provides a comprehensive solution for managing products, stock, sales, and users within an e-commerce platform. It supports CRUD operations, sales reporting, and user authentication. This project is part of a backend challenge for Compass developers.</strong>
</p>

## 🚀 Technologies

- [**Spring Boot**](https://spring.io/projects/spring-boot)
- [**Spring Data JPA**](https://spring.io/projects/spring-data-jpa)
- [**OAuth2**](https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html)
- [**MySQL**](https://dev.mysql.com/downloads/)
- [**Redis**](https://spring.io/projects/spring-data-redis)
- [**MapStruct**](https://mapstruct.org/)
- [**Mockito**](https://site.mockito.org/)
- [**SpringDoc OpenAPI 3**](https://springdoc.org/v2/#spring-webflux-support)
- [**Swagger**](https://swagger.io/)

## 📜 Practices Adopted

- **Layered Architecture**: Separation of concerns with clear boundaries between layers.
- **REST API**: Design and implementation of a REST API.
- **Validation**: Input validation to ensure data integrity.
- **Exception Handling**: Standardized error handling across the application.
- **Security**: JWT authentication and authorization for secure access.
- **Caching**: Efficient data retrieval using Redis for caching.
- **Documentation**: API documentation with Swagger and OpenAPI 3.
- **Unit Testing**: Employs Mockito to write unit tests that isolate and verify the functionality of individual components.

## 🏛️ Architecture

<p>
  <img src="docs/Diagrama ER.png" alt="ER Diagram" />
</p>

## 📦 Getting Started

1. **Clone the Repository**

   ```bash
   $ git clone git@github.com:ReinanPL/ecommerce-api.git

2. **Navigate to the Project Directory**
    ```bash
    $ cd ecommerce-api

3. **Start Docker Containers**
    ```bash
    $ docker compose up -d

4. **Access the API**
   <br><br>
    The API will be available at [localhost:8080](http://localhost:8080).
    The Swagger UI for API documentation can be accessed at [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
    <br><br>
    To access the API with accounts already registered in the database, use the following login details:
    <br>
    Admin - CPF: 25916279086 | Password:123456
    <br>
    Client - CPF: 63235354085 | Password:123456
