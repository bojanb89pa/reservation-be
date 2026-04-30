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

This is a two-service Kotlin/Spring Boot backend using **hexagonal architecture** (ports & adapters).

### Services

- **auth-service** (port 8081): User registration, authentication, JWT token issuance via Spring Authorization Server (OAuth2).
- **resource-service** (port 8080): Business management, availability scheduling, and reservation CRUD. Acts as an OAuth2 resource server — validates JWTs issued by auth-service.

Each service has its own PostgreSQL database (no cross-service DB calls):
- `reservation_auth` on port 5433
- `resource_resource` on port 5432

### Module Layout

Each service is split into submodules following the hexagonal pattern:

```
:domain                    ← shared pure-Kotlin models, service interfaces, repository ports (no Spring)
:auth-service
  :auth-service:api        ← REST controllers (Spring Web only)
  :auth-service:application← @Service implementations of domain interfaces
  :auth-service:data       ← JPA entities, Spring Data repos, repository adapters
:resource-service
  :resource-service:api
  :resource-service:application
  :resource-service:data
```

**Dependency flow:** `api → application → domain (interfaces) ← data (adapters)`

### Key Patterns

- **Repository ports**: interfaces live in `:domain` (e.g., `UserRepositoryPort`), implemented by `*RepositoryAdapter` classes in `:data` submodules. Services in `:application` depend only on the port interfaces.
- **JWT flow**: auth-service signs tokens and embeds user roles as claims; resource-service's `JwtToUserConverter` extracts an `AuthenticatedUser` from the JWT without calling auth-service.
- **Database migrations**: Liquibase, changelogs under `src/main/resources/db/changelog/` in each data submodule.
- **Kotlin `all-open` plugin**: applied project-wide so JPA entity classes work with Kotlin's default `final` modifier.

## Tech Stack

| | |
|---|---|
| Language | Kotlin 1.9.25, JDK 21 |
| Framework | Spring Boot 3.4.5 |
| Security | Spring Security OAuth2 / Authorization Server |
| ORM | Spring Data JPA (Hibernate) |
| DB | PostgreSQL 18 |
| Migrations | Liquibase 4.29.2 |
| Build | Gradle 8 (Kotlin DSL), version catalog at `gradle/libs.versions.toml` |
