# Modules

## :domain

**Responsibility:** Shared, framework-free Kotlin library. Defines the system's data model and all operation contracts.

**Contents:**
- `model/` — domain data classes: `Business`, `Reservation`, `User`, `AuthenticatedUser`, `Role`
- `usecase/` — use case interfaces (inbound ports), one interface per operation
- `port/` — repository port interfaces (outbound ports): `BusinessRepositoryPort`, `ReservationRepositoryPort`, `UserRepositoryPort`
- `constants/` — `SecurityConstants`, `DateConstants`

**Allowed dependencies:** Kotlin stdlib only.
**Forbidden:** Any `org.springframework.*`, `jakarta.*`, or I/O imports.

---

## :auth-service (root)

**Responsibility:** Spring Boot entry point and Spring Authorization Server configuration.

**Contents:** `AuthServerApplication`, security filter chains (`DefaultSecurityConfig`, `ApiServerSecurityConfig`), JWT key setup (`JwtKeyConfig`), token customizer (`JwtTokenCustomizer`), OAuth2 client config (`ClientConfig`).

**Allowed dependencies:** Spring Security, Spring Authorization Server, `:domain`.
**Forbidden:** Business logic, REST controllers, JPA entities.

---

## :auth-service:api

**Responsibility:** Inbound HTTP adapters for the auth service.

**Contents:** `UserController`, request/response DTOs, `OpenApiConfig`.

**Allowed dependencies:** Spring Web, `:domain` (use case interfaces and models).
**Forbidden:** `jakarta.persistence.*` imports, business logic in controller bodies.

---

## :auth-service:application

**Responsibility:** Use case implementations for the auth service.

**Contents:** `CreateUserUseCaseImpl`, `GetUserByEmailUseCaseImpl`, `UserDetailsServiceImpl`, `UserDetailsImpl`.

**Allowed dependencies:** Spring (`@Service`, `PasswordEncoder`), `:domain`.
**Forbidden:** `jakarta.persistence.*`, Spring Web annotations, direct injection of `:data` adapter classes.

---

## :auth-service:data

**Responsibility:** Outbound JPA adapters for the auth service.

**Contents:** `UserEntity`, `UserRepository` (Spring Data JPA), `UserRepositoryAdapter`, mapper extension functions (`UserMappers`), Liquibase changelogs.

**Allowed dependencies:** Spring Data JPA, `jakarta.persistence.*`, `:domain`.
**Forbidden:** Returning `UserEntity` outside the adapter class, Spring Web annotations.

---

## :resource-service (root)

**Responsibility:** Spring Boot entry point and OAuth2 resource server security configuration.

**Contents:** `ReservationApplication`, `ResourceServerSecurityConfig`, `JwtToUserConverter`, `ClientConfig`.

**Allowed dependencies:** Spring Security, Spring OAuth2 Resource Server, `:domain`.
**Forbidden:** Business logic, REST controllers, JPA entities.

---

## :resource-service:api

**Responsibility:** Inbound HTTP adapters for the resource service.

**Contents:** `BusinessController`, `ReservationController`, request/response DTOs, `OpenApiConfig`.

**Allowed dependencies:** Spring Web, `:domain`.
**Forbidden:** `jakarta.persistence.*` imports, business logic in controller bodies.

---

## :resource-service:application

**Responsibility:** Use case implementations for the resource service.

**Contents:** `CreateBusinessUseCaseImpl`, `GetBusinessUseCaseImpl`, `CreateReservationUseCaseImpl`, `GetReservationUseCaseImpl`.

**Allowed dependencies:** Spring (`@Service`), `:domain`.
**Forbidden:** `jakarta.persistence.*`, Spring Web annotations, direct injection of `:data` adapter classes.

---

## :resource-service:data

**Responsibility:** Outbound JPA adapters for the resource service.

**Contents:** `BusinessEntity`, `ReservationEntity`, `BusinessAvailabilityEntity`, Spring Data JPA repositories (`BusinessRepository`, `ReservationRepository`), repository adapters (`BusinessRepositoryAdapter`, `ReservationRepositoryAdapter`), mapper extension functions, Liquibase changelogs.

**Allowed dependencies:** Spring Data JPA, `jakarta.persistence.*`, `:domain`.
**Forbidden:** Returning JPA entities outside adapter classes, Spring Web annotations.
