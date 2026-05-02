# CLAUDE.md — resource-service:application

> Load this file + root `CLAUDE.md` when working in `resource-service/application`. Do not load other service or submodule files.

## Role

Orchestration layer for the resource service. Implements use case interfaces declared in `:domain` and contains all business/reservation business logic. No HTTP, no JPA — communicates outward only through domain port interfaces.

## Use Cases in This Module

| Class | Implements |
|---|---|
| `usecase/business/CreateBusinessUseCaseImpl` | `domain.usecase.business.CreateBusinessUseCase` |
| `usecase/business/GetBusinessUseCaseImpl` | `domain.usecase.business.GetBusinessUseCase` |
| `usecase/reservation/CreateReservationUseCaseImpl` | `domain.usecase.reservation.CreateReservationUseCase` |
| `usecase/reservation/GetReservationUseCaseImpl` | `domain.usecase.reservation.GetReservationUseCase` |

## Use Case Implementation Pattern

- Annotate the implementation class with `@Service` — no additional interface needed in this layer.
- Name the class with the `Impl` suffix to distinguish it from the domain interface without needing an import alias:

```kotlin
import rs.neozoic.reservation.domain.usecase.business.CreateBusinessUseCase

@Service
class CreateBusinessUseCaseImpl(
    private val businessRepository: BusinessRepositoryPort
) : CreateBusinessUseCase {
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

## KDoc Rules

Add KDoc to use case implementation classes only when there is implementation-specific behavior not covered by the domain interface KDoc — for example: guard clauses that throw, cross-port orchestration, or side effects.

Do NOT duplicate the domain interface KDoc. A one-line `/** @see domain interface */` is sufficient for pure delegation.

## Gradle Commands
Never run Gradle commands automatically. Print the command for the user:
> Run the command `{command}` and let me know if there is any issue.

## Unit Tests

Tests live in `src/test/kotlin/` mirroring the source package path. Use **Mockito-Kotlin** with direct constructor instantiation — do not use `@ExtendWith`, `@Mock`, or `@InjectMocks`.

```kotlin
class CreateBusinessUseCaseTest {
    private val businessRepository: BusinessRepositoryPort = mock()
    private val useCase = CreateBusinessUseCaseImpl(businessRepository)

    @Test
    fun `execute delegates to repository and returns result`() {
        val business = Business(id = UUID.randomUUID(), name = "Salon One")
        whenever(businessRepository.createBusiness(business)).thenReturn(business)

        val result = useCase(business)

        assertEquals(business, result)
        verify(businessRepository).createBusiness(business)
    }

    @Test
    fun `execute returns null when repository returns null`() {
        val business = Business(id = null, name = "Salon One")
        whenever(businessRepository.createBusiness(business)).thenReturn(null)

        assertNull(useCase(business))
    }
}
```

For use cases that inject multiple ports, pass all of them in the constructor:
```kotlin
class CreateReservationUseCaseTest {
    private val reservationRepository: ReservationRepositoryPort = mock()
    private val businessRepository: BusinessRepositoryPort = mock()
    private val useCase = CreateReservationUseCaseImpl(reservationRepository, businessRepository)
    ...
}
```

- Imports: `kotlin.test.Test`, `kotlin.test.assertEquals`, `kotlin.test.assertNull`, `kotlin.test.assertFailsWith`
- Mockito imports: `org.mockito.kotlin.mock`, `org.mockito.kotlin.whenever`, `org.mockito.kotlin.verify`, `org.mockito.kotlin.never`, `org.mockito.kotlin.any`
- Mock **port interfaces** from `:domain/port/`, never concrete adapter classes.
- One test class per use case. Cover: happy path, null/not-found return, and any guard clauses that throw.
- Use private factory functions for repeated domain model construction (see `CreateReservationUseCaseTest`).
- Run a single test class: `./gradlew :resource-service:application:test --tests "FullyQualifiedClassName"`
