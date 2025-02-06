
# Product Service

## Overview

The **Product Service** is a microservice built with Spring Boot that manages product-related operations of an Ecommerce Application. It provides RESTful API endpoints for creating, updating, retrieving, and deleting product data. This service is designed to work in a distributed architecture, potentially integrating with other services such as [user authentication](https://github.com/ahmadrashidh/user-service/tree/main) and order management.

## Features

- CRUD operations for product management
- RESTful API with structured JSON responses
- Database persistence with Hibernate and JPA
- Secure access using role-based authentication (if applicable)
- Scalability and microservices-friendly architecture

## Technologies Used

- **Java 11+**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **MySQL / PostgreSQL**
- **Maven**

---

## **Design**

The **Product Service** follows a **four-layered architecture**: **Controller**, **Service**, and **Repository**, with an added **Security Layer**. 

- **Controller Layer**: Handles HTTP requests, validates input, and interacts with the service layer. It exposes API endpoints for CRUD operations on products.

- **Service Layer**: Contains the core business logic and handles interactions with the repository layer. It processes data and orchestrates CRUD operations related to the product entity. The service layer also includes logic for managing product data, such as retrieving all products, adding new products, updating existing products, and deleting products.

- **Repository Layer**: Manages data access using **Spring Data JPA**. It provides the interface for interacting with the database through methods like `findAll()`, `findById()`, `save()`, and `deleteById()` for CRUD operations.

- **Security Layer**: Ensures that only authenticated and authorized users can access the protected resources. This layer uses **JWT tokens** for authentication and implements **role-based access control (RBAC)** to restrict operations based on user roles (e.g., admins can perform all CRUD operations while regular users may have restricted access).

---

## **Database Schema**

The **Product Service** uses a relational database to store product data. The schema includes two main tables: `products` and `category`. Below are their descriptions:

### **Products Table**

| Column        | Type           | Description                                           |
|---------------|----------------|-------------------------------------------------------|
| `id`          | `BIGINT`       | A unique identifier for each product (auto-increment) |
| `name`        | `VARCHAR(255)` | The name of the product                               |
| `description` | `TEXT`         | A detailed description of the product                 |
| `price`       | `DECIMAL`      | The price of the product                              |
| `category_id` | `BIGINT`       | The ID of the category this product belongs to        |
| `image`       | `TEXT`         | The public link of the image of the product           |

### **Categories Table**

| Column      | Type         | Description                                          |
|-------------|--------------|------------------------------------------------------|
| `id`        | `BIGINT`     | A unique identifier for each category (auto-increment)|
| `name`      | `VARCHAR(255)`| The name of the category                             |


The `products` table is linked to the `categories` table through the `category_id` column, which represents a **foreign key** relationship. 

---

## **Getting Started**

### **Prerequisites**
- Java 11 or later
- Maven 3.6+
- MySQL or PostgreSQL (or use an in-memory H2 database for testing)

### **Installation**

1. **Clone the repository:**
   ```bash
   git clone https://github.com/ahmadrashidh/product-service.git
   cd product-service
   ```

2. **Configure the database:**  
   Update the `application.properties` file with the correct database credentials.

3. **Build the application:**
   ```bash
   mvn clean install
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

   The service will start at `http://localhost:8080`.

---

## **API Endpoints**

| Method | Endpoint            | Description                  |
|--------|---------------------|------------------------------|
| GET    | `/api/products`     | Get all products            |
| GET    | `/api/products/{id}`| Get product by ID           |
| POST   | `/api/products`     | Create a new product        |
| PUT    | `/api/products/{id}`| Update an existing product  |
| DELETE | `/api/products/{id}`| Delete a product            |

Categories will be created if it doesn't exist otherwise mapped.

---

## **Future Enhancements**

Here are some potential future enhancements for the **Product Service**:

1. **Product Search & Filtering**:  
   Implement advanced search and filtering capabilities, allowing users to search products by name, category, price range, and other attributes.

2. **CRUD operations for Category**:  
   Add functionality for users to rate and review products. This could include average ratings and user-generated reviews for each product.

3. **Integration with order management**