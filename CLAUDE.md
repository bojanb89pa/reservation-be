# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

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

## Architecture

This is a two-service Kotlin/Spring Boot backend using **hexagonal architecture** (ports & adapters) and **CLEAN architecture** use case pattern.

### Services

- **auth-service** (port 8081): User registration, authentication, JWT token issuance via Spring Authorization Server (OAuth2).
- **resource-service** (port 8080): Business management, availability scheduling, and reservation CRUD. Acts as an OAuth2 resource server — validates JWTs issued by auth-service.

Each service has its own PostgreSQL database (no cross-service DB calls):
- `reservation_auth` on port 5433
- `resource_resource` on port 5432

### Module Layout

Each service is split into submodules following the hexagonal pattern:

```
:domain                    ← shared pure-Kotlin models, use case interfaces, repository ports (no Spring)
  model/                   ← domain models (Business, Reservation, User, …)
  usecase/business/        ← CreateBusinessUseCase, GetBusinessUseCase (interfaces)
  usecase/reservation/     ← CreateReservationUseCase, GetReservationUseCase (interfaces)
  usecase/user/            ← CreateUserUseCase, GetUserByEmailUseCase (interfaces)
  port/                    ← BusinessRepositoryPort, ReservationRepositoryPort, UserRepositoryPort

:auth-service
  :auth-service:api        ← REST controllers (Spring Web only)
  :auth-service:application← use case implementations (usecase/user/)
  :auth-service:data       ← JPA entities, Spring Data repos, repository adapters

:resource-service
  :resource-service:api
  :resource-service:application← use case implementations (usecase/business/, usecase/reservation/)
  :resource-service:data
```

**Dependency flow:** `api → application → domain (interfaces) ← data (adapters)`

### Key Patterns

#### Use Cases
Every business operation is a dedicated use case class — one class, one responsibility:
- Interfaces live in `:domain` under `usecase/<domain>/` (e.g., `CreateBusinessUseCase`, `GetBusinessUseCase`).
- Each interface declares a single `operator fun invoke(...)` method, allowing call-site syntax like `createBusinessUseCase(business)`.
- Implementations live in `:application` under the matching `usecase/<domain>/` package, named identically to the interface (different package). They are annotated `@Service` and use a Kotlin import alias to disambiguate: `import … CreateBusinessUseCase as CreateBusinessUseCasePort`.
- Controllers inject individual use case interfaces — never a combined "service" interface.

#### Repository Ports (Hexagonal Adapters)
- Interfaces live in `:domain/port/` (e.g., `BusinessRepositoryPort`).
- Implemented by `*RepositoryAdapter` classes in `:data` submodules, which delegate to Spring Data JPA repositories.
- Use case implementations in `:application` depend only on the port interfaces — never on JPA directly.

#### Other Patterns
- **JWT flow**: auth-service signs tokens and embeds user roles as claims; resource-service's `JwtToUserConverter` extracts an `AuthenticatedUser` from the JWT without calling auth-service.
- **Database migrations**: Liquibase, changelogs under `src/main/resources/db/changelog/` in each data submodule.
- **Kotlin `all-open` plugin**: applied project-wide so JPA entity classes work with Kotlin's default `final` modifier.

### Conventions to Follow

- **New business operation** → create a new use case interface in `:domain/usecase/<domain>/` and a matching implementation class in `:application/usecase/<domain>/`. Never add methods to an existing use case.
- **New data access** → add a method to the relevant `*RepositoryPort` in `:domain/port/` and implement it in the `*RepositoryAdapter` in `:data`.
- **No service god-objects** — a class that handles more than one use case is a design smell.
- **No Spring in `:domain`** — domain interfaces and models must remain framework-free.

## Tech Stack

| | |
|---|---|
| Language | Kotlin 2.x, JDK 21 |
| Framework | Spring Boot 4.0.6 |
| Security | Spring Security OAuth2 / Authorization Server |
| ORM | Spring Data JPA (Hibernate) |
| DB | PostgreSQL 18 |
| Migrations | Liquibase |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Build | Gradle 8 (Kotlin DSL), version catalog at `gradle/libs.versions.toml` |
| Testing | JUnit 5, Mockito-Kotlin (unit tests in `:application` modules) |
