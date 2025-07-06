# 🛒 Real-Time Order Management System (RTOMS)

A scalable, event-driven backend ecosystem built with Java and Spring Boot that orchestrates product ordering, inventory updates, payments, and notifications in real time.

---

## 🚀 Tech Stack

- **Java 24**, **Spring Boot 3**, **Spring Security**, **Hibernate**
- **Apache Kafka** – for asynchronous communication
- **Redis** – high-speed data caching
- **Ehcache** – Hibernate second-level cache
- **PostgreSQL** – relational persistence layer
- **JWT** – authentication and role-based access control
- **Docker & Docker Compose** – microservice containerization
- **Testcontainers**, **JUnit**, **Mockito** – testing suite

---

## 🧱 Microservices Overview

| Service           | Responsibility                                                |
|------------------|----------------------------------------------------------------|
| **Product**       | Manages product catalog with Redis caching                    |
| **Inventory**     | Tracks stock levels, handles stock deduction                  |
| **Order**         | Processes orders, validates stock, emits Kafka events         |
| **Payment**       | Simulates payment processing via Kafka                        |
| **Notification**  | Sends confirmation (via log/email) based on Kafka triggers    |

---

## 🔐 Security

- JWT-based authentication (`Authorization: Bearer <token>`)
- Role-based access control with `@PreAuthorize` (e.g., Admin, Customer)

---

## 📦 Kafka Event Topics

- `order.created`
- `inventory.updated`
- `payment.completed`
- `notification.send`

---

## 🗃️ Database Design

- **PostgreSQL** for persistence
- **Redis** for real-time caching of hot product data
- **Ehcache** for Hibernate-level query optimization

---

## 🧪 Testing & API Docs

- Integration tests with **Testcontainers** for PostgreSQL and Kafka
- **Swagger/OpenAPI** for REST API documentation

---

## 🐳 Dockerized Deployment

- All services containerized via Docker
- Use `docker-compose.yml` to spin up full system locally

```bash
docker-compose up --build


📂 Directory Layout

RTOMS/
├── docker-compose.yml
├── rtoms-realm.json
├── rtoms-parent/
│   └── pom.xml                 # Centralized dependency & plugin management
├── product-service/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/rtoms/product/
│       │   │   ├── controller/
│       │   │   ├── service/
│       │   │   ├── repository/
│       │   │   └── entity/
│       │   └── resources/
│       │       └── application.yml
│       └── test/
│           └── java/com/rtoms/product/
├── order-service/
│   ├── pom.xml
│   └── src/...                 # Same packaging as product-service
├── payment-service/
│   └── src/...
├── inventory-service/
│   └── src/...
├── notification-service/
│   └── src/...
├── gateway-service/ (optional)
│   └── src/...                 # For Swagger, routing, central auth
└── keycloak/
    └── Docker-managed         # Realm import + mounted config


✏️ Future Enhancements :

Add email/SMS integration to Notification service
Payment gateway implementation
Add order tracking & reporting dashboard (React/Angular frontend)
CI/CD pipeline with GitHub Actions or Jenkins

🤝 Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you’d like to change.

👤 Author
Built with ❤️ by Manoj Krushna Mohanta
