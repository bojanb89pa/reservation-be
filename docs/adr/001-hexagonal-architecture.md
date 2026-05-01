# ADR 001 — Adopt Hexagonal Architecture (Ports & Adapters)

**Date:** 2025
**Status:** Accepted

## Context

The project needs a clear separation between business logic and infrastructure (database, HTTP, Spring). Without it, use case logic becomes entangled with framework annotations, JPA details, and controller-specific concerns — making it hard to test or change independently.

## Decision

Adopt Hexagonal Architecture (Ports & Adapters) combined with the Clean Architecture use case pattern across all services.

- All domain models, use case contracts, and repository port interfaces live in a shared `:domain` module with zero framework dependencies.
- Each service splits into `:api` (inbound adapters), `:application` (use case implementations), and `:data` (outbound adapters).
- Dependency flow is enforced structurally: `:api → :application → :domain ← :data`.
- Each use case is a dedicated class with a single `operator fun invoke(...)` method.

## Consequences

**Positive:**
- Use cases in `:application` can be unit-tested with plain Mockito mocks — no Spring context or database required.
- Changing infrastructure (JPA version, security framework) does not touch business logic.
- Clear module boundaries make it unambiguous where each type of code belongs.
- `:domain` evolves independently of any framework lifecycle.

**Negative:**
- More files per feature: an interface in `:domain`, an implementation in `:application`.
- Mapper functions between JPA entities and domain models add a maintenance surface.
- New contributors must learn the layer structure before contributing.

## Alternatives Considered

- **Layered (MVC) architecture:** Simpler to start, but logic tends to drift into framework-coupled services.
- **CQRS with event sourcing:** Over-engineered for current scale and team size.
