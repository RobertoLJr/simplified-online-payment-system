# Simplified Online Payment System

![Java](https://img.shields.io/badge/Java-17%2B-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0%2B-brightgreen?style=flat-square&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15%2B-blue?style=flat-square&logo=postgresql)
![Flyway](https://img.shields.io/badge/Flyway-Database%20Migration-red?style=flat-square&logo=flyway)
![Maven](https://img.shields.io/badge/Maven-3.8%2B-C71A36?style=flat-square&logo=apachemaven)
![Academic](https://img.shields.io/badge/Purpose-Academic%20Learning-yellow?style=flat-square&logo=graduation-cap)
![Status](https://img.shields.io/badge/Status-Learning%20Project-blue?style=flat-square)

> 📚 **Academic Learning Project**: A Spring Boot backend system simulating a simplified online payment platform for educational purposes.

## 🚨 **IMPORTANT SECURITY NOTICE**

> ⚠️ **This is a learning project for academic purposes only!**
> 
> **Known Security Issues (Intentionally Not Implemented for Learning Focus):**
> - ❌ **No Authentication System**: Users can access any endpoint without verification
> - ❌ **No Password Encryption**: Passwords are stored as **plain text** in the database
> - ❌ **No Authorization**: No role-based access controls implemented
> - ❌ **No Input Validation**: Basic Spring Boot validation only
> - ❌ **No HTTPS Enforcement**: Runs on HTTP for development
> - ❌ **No Tests**: No unitary or integration tests were implemented yet.
> 
> **🚫 DO NOT USE IN PRODUCTION** - This project focuses on Spring Boot fundamentals, not security implementation.

## 🎓 Learning Objectives

This project demonstrates:
- ✅ **Spring Boot Application Structure**
- ✅ **RESTful API Development**
- ✅ **JPA/Hibernate Integration**
- ✅ **Database Migration with Flyway**
- ✅ **PostgreSQL Integration**
- ✅ **Maven Project Management**
- ✅ **MVC Architecture Pattern**
- ✅ **DTO Pattern Implementation**
- ✅ **Content Negotiation (supports JSON, XML and YAML)**

## 🚀 Project Overview

A simplified backend simulation of an online payment platform built to explore **Spring Boot fundamentals**. The system supports two user types (regulars and merchants) and basic payment operations, prioritizing learning core Spring Boot concepts over security implementation.

## ✨ Implemented Features

- 👥 **Dual User Roles**: Basic customer and merchant user types
- 💰 **Simple Payment Processing**: Basic transaction creation and storage
- 🗄️ **Database Operations**: Full CRUD operations with JPA
- 🔄 **Database Versioning**: Flyway migration scripts
- 📊 **Transaction History**: Basic transaction logging
- 🌐 **RESTful Endpoints**: Clean API structure
- 🏗️ **Layered Architecture**: Controller → Service → Repository pattern

## 🛠️ Tech Stack

| Technology | Purpose | Learning Focus |
|------------|---------|----------------|
| **Java 17+** | Core language | Object-oriented programming |
| **Spring Boot 3.x** | Application framework | Auto-configuration, dependency injection |
| **Spring Data JPA** | Database layer | ORM concepts, repository pattern |
| **PostgreSQL** | Database | SQL, relational database design |
| **Flyway** | Database migration | Version control for database schema |
| **Maven** | Build tool | Dependency management, project structure |

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.8+
- PostgreSQL 15+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)
- Basic understanding of Spring Boot concepts

## ⚡ Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/RobertoLJr/simplified-online-payment-system.git
cd simplified-online-payment-system
```

### 2. Database Setup
```sql
-- Create database
CREATE DATABASE payment_system_learning;

-- Create user
CREATE USER learning_user WITH PASSWORD 'password123';
GRANT ALL PRIVILEGES ON DATABASE payment_system_learning TO learning_user;
```

### 3. Configuration
Update `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/payment_system_learning
spring.datasource.username=learning_user
spring.datasource.password=password123

# Show SQL queries for learning
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 4. Run the Application
```bash
# Install dependencies and run
mvn spring-boot:run

# View application logs to understand Spring Boot startup process
```

The application will start at `http://localhost:8080`

## 📡 API Endpoints (No Authentication Required)

`The API supports OpenAPI/Swagger documentation. Visit http://localhost:8080/swagger-ui/index.html to view all available endpoints.`

### Example API Usage
```bash
# Create a customer (password in plain text!)
curl -X POST http://localhost:8080/api/users \
-H "Content-Type: application/json" \
-d '{"name":"John Doe","email":"john@example.com","password":"mypassword","userType":"CUSTOMER"}'

# Create a simple transaction
curl -X POST http://localhost:8080/api/transactions \
-H "Content-Type: application/json" \
-d '{"senderId":1,"receiverId":2,"amount":100.00}'
```

## 🏗️ Project Structure (Learning-Focused)

```
src/
├── main/
│   ├── java/com/roberto/paymentsystem/
│   │   ├── PaymentSystemApplication.java   # Spring Boot main class
│   │   ├── config/                         # Spring Boot configuration classes
│   │   │   ├── AppConfig
│   │   │   ├── OpenApiConfig
│   │   │   └── WebConfig
│   │   ├── constant/                       # Entity Enums
│   │   │   ├── notification/
│   │   │   │   ├── Channel.java
│   │   │   │   └── Status.java
│   │   │   ├── transaction/
│   │   │   │   ├── Status.java
│   │   │   └── user/
│   │   │       ├── DocumentType.java
│   │   │       └── UserType.java
│   │   ├── controller/                     # REST controllers (@RestController)
│   │   │   ├── docs/                       # OpenAPI/Swagger interface
│   │   │   │   ├── NotificationControllerDocs.java
│   │   │   │   ├── TransactionControllerDocs.java
│   │   │   │   └── UserControllerDocs.java
│   │   │   ├── NotificationController.java
│   │   │   ├── TransactionController.java
│   │   │   └── UserController.java
│   │   ├── dto/                            # Data Transfer Objects
│   │   │   ├── notification/...
│   │   │   └── Transaction/...
│   │   │   └── user/...
│   │   ├── entity/                         # JPA Entities (@Entity)
│   │   │   ├── Notification.java
│   │   │   └── Transaction.java
│   │   │   └── User.java
│   │   ├── exception/                     # Custom Exceptions
│   │   │   ├── handler/...
│   │   │   ├── notification/...
│   │   │   ├── transaction/...
│   │   │   ├── user/...
│   │   │   └── ExceptionResponse.java
│   │   ├── mapper/                         # MapStruct mappers
│   │   ├── repository/                     # Data Repositories (@Repository)
│   │   │   ├── NotificationRepository.java
│   │   │   ├── TransactionRepository.java
│   │   │   └── UserRepository.java
│   │   └── service/                        # Business Logic (@Service)
│   │       ├── NotificationService.java
│   │       ├── TransactionService.java
│   │       └── UserService.java
│   └── resources/
│       ├── db/migration/                   # Flyway migration files
│       │   ├── V1__create_users_table.sql
│       │   ├── V2__create_transactions_table.sql
│       │   └── V3__create_notifications_table.sql
│       └── application.properties          # Spring Boot configuration
└── test/                                   # Unit tests (NOT IMPLEMENTED)
```

---

*🎓 This is an academic project designed to demonstrate Spring Boot learning progress. It intentionally focuses on framework fundamentals rather than production-ready security practices.*