# Reservation Management System

A Kotlin/Spring Boot backend for **businesses to manage reservations**, built with Hexagonal Architecture and Clean Architecture use case pattern.

Two independent microservices, each with its own database and module structure:

| Service | Port | Database | Role |
|---|---|---|---|
| `auth-service` | 8081 | `reservation_auth` | User registration, authentication, JWT issuance |
| `resource-service` | 8080 | `reservation_resource` | Business management, availability, reservations |

---

## Getting Started

### Prerequisites

- JDK 21+
- Docker & Docker Compose

### Start databases

```bash
docker compose up -d
```

### Run services

```bash
./gradlew :auth-service:bootRun      # http://localhost:8081
./gradlew :resource-service:bootRun  # http://localhost:8080
```

### API Documentation (Swagger UI)

- Auth service: `http://localhost:8081/swagger-ui.html`
- Resource service: `http://localhost:8080/swagger-ui.html`

---

## Tech Stack

| | |
|---|---|
| Language | Kotlin 2.x, JDK 21 |
| Framework | Spring Boot 4.0.6 |
| Security | Spring Security OAuth2 / Authorization Server |
| ORM | Spring Data JPA (Hibernate) |
| DB | PostgreSQL 18 |
| Migrations | Liquibase |
| API Docs | SpringDoc OpenAPI |
| Build | Gradle 8 (Kotlin DSL) |
| Testing | JUnit 5, Mockito-Kotlin |

---

## Module Overview

```
project-root/
├── domain/              # Shared use case interfaces, repository ports, domain models (no Spring)
├── auth-service/        # Authorization Server root (security config, JWT signing)
│   ├── api/             # REST controllers (inbound adapters)
│   ├── application/     # Use case implementations
│   └── data/            # JPA entities, repositories, adapters (outbound adapters)
├── resource-service/    # Resource Server root (security config, JWT validation)
│   ├── api/
│   ├── application/
│   └── data/
└── compose.yaml         # Local PostgreSQL instances
```

**Dependency flow:** `api → application → domain ← data`

---

## Authentication Flow

1. Client authenticates against `auth-service /oauth2/token`.
2. JWT is issued with `user_id`, `email`, and `roles` as custom claims.
3. Client sends `Bearer <token>` to `resource-service`.
4. `resource-service` validates the JWT via the auth-service JWK endpoint — no runtime call to auth-service.

---

## Testing

```bash
./gradlew test                        # all tests
./gradlew :resource-service:test      # single service
```

Unit tests cover each use case in `:application` using Mockito-Kotlin mocks — no Spring context required.

---

## Documentation

### [Architecture](docs/architecture.md)
Explains the hexagonal architecture (Ports & Adapters) pattern used across all services, including:
- Inbound and outbound port definitions
- Dependency direction (`api → application → domain ← data`)
- Module boundary rules and why they exist

### [Modules](docs/modules.md)
Per-module reference covering every Gradle submodule:
- Responsibility and contents of each module
- Allowed and forbidden dependencies
- Applies to both `auth-service` and `resource-service` submodule sets

### [Use Cases](docs/use-cases.md)
Business flow documentation for all application use cases:
- `CreateUserUseCase`, `GetUserByEmailUseCase` (auth-service)
- `CreateBusinessUseCase`, `GetBusinessUseCase`, `CreateReservationUseCase`, `GetReservationUseCase` (resource-service)
- End-to-end JWT authentication flow

### [Security](docs/security.md)
Practical guide to authentication and OAuth2:
- OAuth2 endpoints and registered client credentials
- JWT token structure and custom claims (`sub`, `email`, `roles`)
- Step-by-step token acquisition via `curl`, Swagger UI, and Postman
- Endpoint access rules (public vs. protected) for both services
- Known development limitations (in-memory stores, ephemeral keys, role extraction bug)

### [ADRs](docs/adr/)
Architecture Decision Records capturing key design choices:
- [ADR 001 — Hexagonal Architecture](docs/adr/001-hexagonal-architecture.md): context, decision, consequences, and alternatives considered

---

## License

[Apache 2.0](LICENSE) — Maintainer: Bojan Bogojević (bojanb89@gmail.com)
