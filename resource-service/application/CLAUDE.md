# CLAUDE.md — resource-service:application

> Load this file + root `CLAUDE.md` when working in `resource-service/application`. Do not load other service or submodule files.

## Role

Orchestration layer for the resource service. Implements use case interfaces declared in `:domain` and contains all business/reservation business logic. No HTTP, no JPA — communicates outward only through domain port interfaces.

## Use Cases in This Module

| Class | Implements |
|---|---|
| `usecase/business/CreateBusinessUseCase` | `domain.usecase.business.CreateBusinessUseCase` |
| `usecase/business/GetBusinessUseCase` | `domain.usecase.business.GetBusinessUseCase` |
| `usecase/reservation/CreateReservationUseCase` | `domain.usecase.reservation.CreateReservationUseCase` |
| `usecase/reservation/GetReservationUseCase` | `domain.usecase.reservation.GetReservationUseCase` |

## Use Case Implementation Pattern

- Annotate the implementation class with `@Service` — no additional interface needed in this layer.
- Use a Kotlin import alias to resolve the name collision between the domain interface and the implementation class:

```kotlin
import rs.neozoic.reservation.domain.usecase.business.CreateBusinessUseCase as CreateBusinessUseCasePort

@Service
class CreateBusinessUseCase(
    private val businessRepository: BusinessRepositoryPort
) : CreateBusinessUseCasePort {
    override operator fun invoke(business: Business): Business? =
        businessRepository.createBusiness(business)
}
```

- Implement `operator fun invoke(...)` — call-site in controllers becomes `createBusinessUseCase(business)`.

## Dependency Rules

- **Inject domain port interfaces** (from `:domain/port/`) — never inject `:data` adapter classes directly.
- No `jakarta.persistence` imports. No JPA types.
- No Spring Web annotations (`@RestController`, `@RequestMapping`, etc.).

## Adding a New Use Case

1. Add the interface to `:domain/usecase/<domain>/` with a single `operator fun invoke(...)`.
2. Add the `@Service` implementation here under `usecase/<domain>/`.
3. Never add a second method to an existing use case class — create a new one.

## Testing

Unit tests live in `src/test/kotlin/`. Use JUnit 5 + Mockito-Kotlin. Mock the port interface, never the adapter.

```kotlin
@ExtendWith(MockitoExtension::class)
class CreateBusinessUseCaseTest {
    @Mock lateinit var businessRepository: BusinessRepositoryPort
    @InjectMocks lateinit var useCase: CreateBusinessUseCase

    @Test
    fun `invoke delegates to repository`() { ... }
}
```
