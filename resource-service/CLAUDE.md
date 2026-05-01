# CLAUDE.md — resource-service (root)

> Load this file + root `CLAUDE.md` when editing the `resource-service` root module. For submodule work (api, application, data), load that submodule's `CLAUDE.md` instead of this one.

## Role

Spring Boot entry point and security configuration for the **OAuth2 Resource Server** (port 8080). Manages businesses, availability schedules, and reservations. Validates JWTs issued by `auth-service` — no runtime calls back to auth-service.

## Responsibilities

| File | Purpose |
|---|---|
| `ReservationApplication` | `@SpringBootApplication` entry point |
| `ResourceServerSecurityConfig` | JWT-based resource server security — validates tokens, enforces role-based access |
| `JwtToUserConverter` | Extracts `AuthenticatedUser` (with roles) from JWT claims; no auth-service call at runtime |
| `ClientConfig` | OAuth2 client config for service-to-service communication if needed |

## Key Facts

- Database: `resource_resource` on port `5432`
- Accepts Bearer tokens issued by `auth-service`; the RSA public key is fetched from auth-service's JWK endpoint at startup only.
- `AuthenticatedUser` (from `:domain`) is the in-process principal representation — roles come from JWT claims, not a DB lookup.

## What Belongs Here

- `@Configuration` beans for resource server security, JWT conversion, CORS.
- `@SpringBootApplication` class.
- `application.yml` / `application-*.yml` for this service.

## What Does NOT Belong Here

- Business logic → `:resource-service:application`
- REST controllers → `:resource-service:api`
- JPA entities, Spring Data repos → `:resource-service:data`
