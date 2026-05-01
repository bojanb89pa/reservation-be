# Architecture

## Overview

The system uses **Hexagonal Architecture** (Ports & Adapters) combined with **Clean Architecture** use case pattern. The goal is to keep domain logic isolated from all infrastructure concerns — frameworks, databases, and HTTP.

Domain logic depends on nothing external. Frameworks depend on the domain, never the reverse.

## Ports & Adapters

### Inbound Ports (Driving Side)
Use case interfaces in `:domain/usecase/` define what the application can do. REST controllers in `:api` are the inbound adapters — they translate HTTP requests into domain operations and delegate to use case implementations.

### Outbound Ports (Driven Side)
Repository port interfaces in `:domain/port/` define what the application needs from infrastructure. JPA adapters in `:data` implement these interfaces, translating between JPA entities and domain models.

## Dependency Direction

```
:api → :application → :domain ← :data
```

- `:api` — depends on `:domain` (use case interfaces, models) and `:application` (resolved by Spring component scan)
- `:application` — depends on `:domain` only; no JPA, no HTTP
- `:data` — depends on `:domain` only; implements outbound ports
- `:domain` — depends on nothing; pure Kotlin

## Module Boundaries

| Rule | Reason |
|---|---|
| No Spring annotations in `:domain` | Domain must remain framework-free and independently testable |
| No JPA types returned from `:data` adapters | Prevents ORM details leaking into business logic |
| No `:data` class injection in `:application` | Use cases depend on abstractions, not implementations |
| No business logic in `:api` controllers | Controllers own HTTP concerns only; use cases own logic |

## Services

| Service | Port | Database | Role |
|---|---|---|---|
| `auth-service` | 8081 | `reservation_auth` (5433) | OAuth2 Authorization Server — JWT issuance, user registration |
| `resource-service` | 8080 | `reservation_resource` (5432) | OAuth2 Resource Server — business and reservation management |

Services do not share a database and do not make runtime calls to each other. Communication is via signed JWTs only.

## Module Dependency Graph

See `docs/module-graph.puml` for a PlantUML diagram of module dependencies.
