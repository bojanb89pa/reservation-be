# ADR 002 — Split into Two Microservices: Auth and Resource

**Date:** 2025
**Status:** Accepted

## Context

The system needs both an identity/authentication layer and a business domain layer (businesses, resources, reservations). Bundling these into a single service would couple the OAuth 2.0 lifecycle — token issuance, client registration, user credentials — with unrelated business operations. It would also make it harder to scale or evolve each concern independently.

## Decision

Run two separate Spring Boot microservices:

- **`auth-service` (port 8081)** — acts as an OAuth 2.0 Authorization Server (Spring Authorization Server). It is the single source of truth for user identities and credentials. User accounts are stored here and managed here exclusively; no other service writes to the auth database.
- **`resource-service` (port 8080)** — acts as an OAuth 2.0 Resource Server. It holds all business domain data: businesses, resources, and reservations. It validates JWTs issued by `auth-service` but never manages credentials or user accounts directly.

Each service owns its own PostgreSQL database (`reservation_auth` on port 5433, `resource_resource` on port 5432). There are no cross-service database calls.

## Consequences

**Positive:**
- Security-sensitive code (password hashing, token signing keys, user credentials) is isolated in `auth-service` and cannot be accidentally exposed through a resource endpoint.
- `resource-service` can evolve its domain model freely without touching auth concerns.
- Each service can be deployed, scaled, and restarted independently.
- Clear operational boundary: if auth is down, tokens cannot be issued, but existing valid tokens still allow resource access until expiry.

**Negative:**
- Two services to run locally and in production instead of one.
- User identity (id, roles) must be propagated via JWT claims; any new claim requires a coordinated change in both services.
- No shared database means user data visible in the resource domain (e.g., reservation owner name) must be passed at write time or resolved via an API call, not a join.

## Alternatives Considered

- **Single monolith:** Simpler local setup, but mixes security-critical auth code with business logic and makes future extraction harder.
- **Shared database with two deployable units:** Avoids the data-propagation problem but creates hidden coupling between services that breaks independent deployability.
