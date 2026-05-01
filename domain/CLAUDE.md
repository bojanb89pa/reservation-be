# CLAUDE.md — :domain

> Load this file + root `CLAUDE.md` when working in the `domain` module. Do not load service or submodule files.

## Role

Shared, framework-free Kotlin library consumed by both `auth-service` and `resource-service`. Contains all domain models, use case contracts (inbound ports), and repository port contracts (outbound ports). Nothing here knows Spring exists.

## Hard Rules

- **Zero Spring dependencies.** No `org.springframework` imports of any kind. No `@Service`, `@Entity`, `@Autowired`, `@Component`, or `@Repository`.
- **No I/O, no persistence.** Port interfaces declare operations in terms of domain models only; implementations live in `:data`.
- **No mutable global state.**
- Kotlin `all-open` plugin is applied project-wide — do not rely on class finality.

## Package Structure

```
rs.neozoic.reservation.domain
  model/
    Business.kt
    Reservation.kt
    User.kt
    AuthenticatedUser.kt
    Role.kt
  usecase/
    business/     ← CreateBusinessUseCase, GetBusinessUseCase
    reservation/  ← CreateReservationUseCase, GetReservationUseCase
    user/         ← CreateUserUseCase, GetUserByEmailUseCase
  port/
    BusinessRepositoryPort.kt
    ReservationRepositoryPort.kt
    UserRepositoryPort.kt
  constants/
    SecurityConstants.kt
    DateConstants.kt
```

## Domain Models

Use Kotlin `data class`. Expose only fields meaningful to business logic — never internal DB keys (e.g., use `publicId` as the public `id`, not the auto-generated `Long`).

```kotlin
// model/Business.kt
data class Business(val id: UUID? = null, val name: String)
```

## Use Case Interfaces (Inbound Ports)

- One interface per operation; one method per interface — never add a second method to an existing interface.
- The single method **must** be `operator fun invoke(...)` to enable call-site syntax `useCase(args)`.
- Placed under `usecase/<domain>/` matching the business capability.

```kotlin
// usecase/business/CreateBusinessUseCase.kt
interface CreateBusinessUseCase {
    operator fun invoke(business: Business): Business?
}
```

## Repository Ports (Outbound Ports)

- Placed under `port/`, named `[Entity]RepositoryPort`.
- All parameters and return types are domain models — no JPA types, no `Optional`.

```kotlin
// port/BusinessRepositoryPort.kt
interface BusinessRepositoryPort {
    fun createBusiness(business: Business): Business?
    fun getBusinessByPublicId(publicId: UUID): Business?
    fun existByPublicId(publicId: UUID): Boolean
}
```
