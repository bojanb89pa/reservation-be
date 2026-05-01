# CLAUDE.md — auth-service (root)

> Load this file + root `CLAUDE.md` when editing the `auth-service` root module. For submodule work (api, application, data), load that submodule's `CLAUDE.md` instead of this one.

## Role

Spring Boot entry point and security configuration for the **OAuth2 Authorization Server** (port 8081). Responsible for user authentication, JWT signing, and token issuance. Does not contain business logic — that lives in `:auth-service:application`.

## Responsibilities

| File | Purpose |
|---|---|
| `AuthServerApplication` | `@SpringBootApplication` + `@EnableJpaRepositories` entry point |
| `AuthorizationServerConfig` | Spring Authorization Server setup — token endpoint, JWK endpoint, OIDC |
| `DefaultSecurityConfig` | Security filter chain for non-API routes (form login, CORS, CSRF rules) |
| `ApiServerSecurityConfig` | Security filter chain for `/api/**` routes |
| `JwtKeyConfig` | RSA key pair generation/loading for JWT signing |
| `JwtTokenCustomizer` | Embeds user roles as custom JWT claims — consumed by resource-service |
| `ClientConfig` | OAuth2 registered client configuration |

## Key Facts

- Database: `reservation_auth` on port `5433`
- JWTs are signed with an RSA key configured here; `resource-service` validates them via the JWK endpoint — no runtime callback.
- Roles embedded by `JwtTokenCustomizer` are extracted by `resource-service`'s `JwtToUserConverter` without calling auth-service.
- Swagger UI (`/swagger-ui/**`, `/v3/api-docs/**`) is excluded from the security filter chain via `WebSecurityCustomizer`.

## What Belongs Here

- `@Configuration` beans for Spring Authorization Server, security filter chains, JWT, CORS, and OAuth2 clients.
- `@SpringBootApplication` class.
- `application.yml` / `application-*.yml` for this service.

## What Does NOT Belong Here

- Business / user logic → `:auth-service:application`
- REST controllers → `:auth-service:api`
- JPA entities, Spring Data repos → `:auth-service:data`
