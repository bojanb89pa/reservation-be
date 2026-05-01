# Reservation Management System

A modular Kotlin-based Spring Boot project designed for **businesses to manage reservations**. The system is composed of two independent microservices:

* **`auth-service`** — Responsible for **authentication**, **authorization**, and **user management**.
* **`resource-service`** — Manages **businesses**, **availability**, and **reservations**.

---

## Features

* JWT-based authentication and role-based authorization
* Admin and user management
* Business availability and reservation scheduling
* Spring Boot + Kotlin with **CLEAN architecture** and **hexagonal (ports & adapters)** design
* PostgreSQL databases with Liquibase migrations
* Docker Compose support for development
* Swagger UI via SpringDoc OpenAPI

---

## Project Architecture

The system follows **CLEAN architecture** combined with **hexagonal architecture** (ports & adapters). Each service is split into four submodules:

```
project-root/
├── domain/              # Shared use case interfaces, repository ports, domain models
├── auth-service/
│   ├── api/             # REST controllers
│   ├── application/     # Use case implementations
│   └── data/            # JPA entities, repositories, repository adapters
├── resource-service/
│   ├── api/
│   ├── application/
│   └── data/
└── docker-compose.yml   # Local PostgreSQL instances
```

**Dependency flow:** `api → application → domain ← data`

### Domain module (`:domain`)

* Contains **use case interfaces**, **repository port interfaces**, and **domain models** — no Spring, no JPA.
* Use case interfaces are organized by domain under `usecase/<domain>/` (e.g., `usecase/business/`, `usecase/reservation/`, `usecase/user/`).
* Repository port interfaces live under `port/` (e.g., `BusinessRepositoryPort`).

### Application submodule (`*:application`)

* Contains **use case implementations** — one class per use case, one method (`operator fun invoke`).
* Classes are named identically to their domain interface (e.g., `CreateBusinessUseCase`) and placed in the matching `usecase/<domain>/` package.
* Depend only on domain interfaces — never on JPA or Spring Data directly.

### Data submodule (`*:data`)

* Contains **JPA entities**, **Spring Data repositories**, and **repository adapters** that implement the domain port interfaces.
* Adapters translate between JPA entities and domain models using mapper extension functions.

### API submodule (`*:api`)

* Contains **REST controllers** that inject individual use case interfaces and delegate to them.
* No business logic — controllers only handle HTTP concerns (request mapping, response codes).

### Use Case pattern

Every business operation is a dedicated use case:

```
domain/usecase/business/CreateBusinessUseCase.kt  ← interface with operator fun invoke(...)
application/usecase/business/CreateBusinessUseCase.kt  ← @Service implementation
```

Controllers call use cases as functions thanks to `operator fun invoke`:

```kotlin
// BusinessController
createBusinessUseCase(business)   // calls CreateBusinessUseCase.invoke(business)
getBusinessUseCase(id)            // calls GetBusinessUseCase.invoke(id)
```

---

## Getting Started

### Prerequisites

* JDK 21+
* Docker & Docker Compose

### Start databases

```bash
docker compose up -d
```

### Run services

```bash
./gradlew :auth-service:bootRun      # http://localhost:8081
./gradlew :resource-service:bootRun  # http://localhost:8080
```

### API Documentation

Swagger UI is available at:
* auth-service: `http://localhost:8081/swagger-ui.html`
* resource-service: `http://localhost:8080/swagger-ui.html`

---

## Authentication & Authorization

* Users authenticate via **auth-service** and receive a JWT.
* JWT tokens include `user_id`, `email`, and `roles`.
* Resource service validates tokens and extracts the authenticated user via a custom `JwtToUserConverter` — no runtime calls to auth-service.

---

## Testing

```bash
./gradlew test                        # all tests
./gradlew :resource-service:test      # single service
```

Unit tests cover each use case implementation in isolation using Mockito-Kotlin mocks for repository ports.

---

## License

This project is licensed under the [Apache 2.0 License](LICENSE).

---

## Contact

Maintainer: Bojan Bogojević (bojanb89@gmail.com)
