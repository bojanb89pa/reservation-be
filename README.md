# Reservation Management System

A modular Kotlin-based Spring Boot project designed for **businesses to manage reservations**. The system is composed of two independent microservices:

* **`auth-service`** — Responsible for **authentication**, **authorization**, and **user management**.
* **`resource-service`** — Manages **businesses**, **availability**, and **reservations**.

## ✨ Features

* 🔐 JWT-based authentication and role-based authorization
* 🧑‍💼 Admin and user management
* 📅 Business availability and reservation scheduling
* ☁️ Spring Boot + Kotlin with a **modular CLEAN architecture**
* 🔄 PostgreSQL databases with Liquibase migrations
* 🐳 Docker Compose support for development

---

## 🧱 Project Architecture

The system is organized using **modular CLEAN architecture**, where each service has three layers:

```
📦 project-root
├── 📂 auth-service
│   ├── 📂 data           # Entities, JPA repositories
│   ├── 📂 application    # Service implementations
│   ├── 📂 api            # REST controllers, request/response models
├── 📂 resource-service
│   ├── 📂 data
│   ├── 📂 application
│   ├── 📂 api
├── 📂 domain             # Shared domain models and interfaces (used by both services)
├── 🐳 docker-compose.yml # Local PostgreSQL instances
```

### 🧼 CLEAN Architecture Breakdown

* **Domain module** (`:domain`):

    * Contains core interfaces (e.g. repositories, services), domain models (e.g., `User`, `Reservation`, `Business`)
    * No external dependencies — pure business logic

* **Data submodule** (`auth-service:data`, `resource-service:data`):

    * Contains JPA entities, JPA repositories and repository implementations that implement domain interfaces
    * Responsible for persistence layer

* **Application submodule** (`auth-service:application`, `resource-service:application`):

    * Contains service implementations
    * Uses interfaces from the domain layer

* **API submodule** (`auth-service:api`, `resource-service:api`):

    * Exposes REST controllers

---

## 🚀 Getting Started

### Prerequisites

* Java 17+
* Kotlin 1.9+
* Gradle
* Docker & Docker Compose

### Start PostgreSQL with Docker Compose

```bash
docker-compose up -d
```

### Run Auth Service

```bash
./gradlew :auth-service:bootRun
```

### Run Resource Service

```bash
./gradlew :resource-service:bootRun
```

Both services run independently:

* `auth-service` ➜ [http://localhost:8081](http://localhost:8081)
* `resource-service` ➜ [http://localhost:8080](http://localhost:8080)

---

## 🔐 Authentication & Authorization

* Users authenticate via **auth-service**
* JWT tokens include `user_id`, `email`, and `roles`
* Resource service extracts these claims via a custom JWT converter to identify the authenticated user

---

## 🧪 Testing

```bash
./gradlew test
```

---

## 📄 License

This project is licensed under the [Apache 2.0 License](LICENSE).

---

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

---

## 📬 Contact

Maintainer: Bojan Bogojević (bojanb89@gmail.com)

Feel free to reach out with suggestions, ideas, or improvements.

---

## ✅ TODO

* [ ] Add unit/integration tests
* [ ] Add Swagger or SpringDoc for API docs
* [ ] Add Redis for token blacklisting (optional)
* [ ] CI/CD pipeline with GitHub Actions


