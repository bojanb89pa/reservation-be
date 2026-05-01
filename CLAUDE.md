# CLAUDE.md — Global Rules

## AI Workflow
When working on a specific task, load **only this file plus the `CLAUDE.md` of the exact module you are editing**. Do not load other modules' files. Context isolation keeps each session focused and prevents architectural cross-contamination.

## Commands

```bash
# Build
./gradlew build

# Run services (each in its own terminal)
./gradlew :auth-service:bootRun      # port 8081
./gradlew :resource-service:bootRun  # port 8080

# Tests
./gradlew test                            # all tests
./gradlew :resource-service:test          # single module
./gradlew test --tests "FullyQualifiedClassName.methodName"  # single test

# Start databases (required before running services)
docker compose up -d
```

## Tech Stack

| | |
|---|---|
| Language | Kotlin 2.x, JDK 21 |
| Framework | Spring Boot 4.0.6 |
| Security | Spring Security OAuth2 / Authorization Server |
| ORM | Spring Data JPA (Hibernate) |
| DB | PostgreSQL 18 |
| Migrations | Liquibase (`src/main/resources/db/changelog/`) |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Build | Gradle 8 (Kotlin DSL), version catalog at `gradle/libs.versions.toml` |
| Testing | JUnit 5, Mockito-Kotlin (unit tests in `:application` modules) |

## Architecture

Hexagonal architecture (Ports & Adapters) + Clean Architecture use case pattern across two services.

```
:domain                    ← pure Kotlin, no Spring, shared by all services
:auth-service              ← OAuth2 Authorization Server (JWT issuer), port 8081
  :auth-service:api        ← REST controllers (inbound adapters)
  :auth-service:application← use case implementations
  :auth-service:data       ← JPA entities + repository adapters (outbound adapters)
:resource-service          ← OAuth2 Resource Server (business/reservation CRUD), port 8080
  :resource-service:api
  :resource-service:application
  :resource-service:data
```

**Dependency flow:** `api → application → domain (interfaces) ← data (adapters)`

Each service owns its own PostgreSQL database — no cross-service DB calls:
- `reservation_auth` on port 5433
- `resource_resource` on port 5432

## Global Naming Conventions

- **One class, one responsibility.** No god objects — a class handling more than one use case is a design smell.
- Use case interfaces (in `:domain`): `VerbNounUseCase` (e.g., `CreateBusinessUseCase`, `GetReservationUseCase`)
- Use case implementations (in `:application`): same name, different package; use import alias to disambiguate
- Repository ports (in `:domain/port/`): `NounRepositoryPort` (e.g., `BusinessRepositoryPort`)
- Repository adapters (in `:data`): `NounRepositoryAdapter` (e.g., `BusinessRepositoryAdapter`)
- Each use case interface declares **exactly one** `operator fun invoke(...)` method

## Hard Rules

- **No Spring in `:domain`** — zero `org.springframework` imports; pure Kotlin only.
- **No cross-service DB calls** — each service reads and writes only its own schema.
- **No combined service interfaces** — controllers inject individual use case interfaces, never an aggregate service.
- **Never add methods to an existing use case** — create a new use case class instead.
- **New business operation** → new interface in `:domain/usecase/<domain>/` + new implementation in `:application/usecase/<domain>/`.
- **New data access** → new method on the relevant `*RepositoryPort` in `:domain/port/` + implementation in `:data`.
