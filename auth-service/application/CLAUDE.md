# CLAUDE.md — auth-service:application

> Load this file + root `CLAUDE.md` when working in `auth-service/application`. Do not load other service or submodule files.

## Role

Orchestration layer for the auth service. Implements use case interfaces declared in `:domain` and contains all user-related business logic. No HTTP, no JPA — communicates outward only through domain port interfaces.

## Use Cases in This Module

| Class | Implements |
|---|---|
| `usecase/user/CreateUserUseCase` | `domain.usecase.user.CreateUserUseCase` |
| `usecase/user/GetUserByEmailUseCase` | `domain.usecase.user.GetUserByEmailUseCase` |

Supporting Spring Security integration:
- `UserDetailsServiceImpl` — implements `UserDetailsService`; delegates to `GetUserByEmailUseCase`
- `UserDetailsImpl` — wraps `domain.model.User` as a Spring Security `UserDetails`

## Use Case Implementation Pattern

- Annotate the implementation class with `@Service` — no additional interface needed in this layer.
- Use a Kotlin import alias to resolve the name collision between the domain interface and the implementation class:

```kotlin
import rs.neozoic.reservation.domain.usecase.user.CreateUserUseCase as CreateUserUseCasePort

@Service
class CreateUserUseCase(
    private val userRepository: UserRepositoryPort
) : CreateUserUseCasePort {
    override operator fun invoke(user: User): User? =
        userRepository.createUser(user)
}
```

- Implement `operator fun invoke(...)` — call-site in controllers becomes `createUserUseCase(user)`.

## Dependency Rules

- **Inject domain port interfaces** (from `:domain/port/`) — never inject `:data` adapter classes directly.
- No `jakarta.persistence` imports. No JPA types.
- No Spring Web annotations (`@RestController`, `@RequestMapping`, etc.).

## Adding a New Use Case

1. Add the interface to `:domain/usecase/user/` with a single `operator fun invoke(...)`.
2. Add the `@Service` implementation here under `usecase/user/`.
3. Never add a second method to an existing use case class — create a new one.

## KDoc Rules

Add KDoc to use case implementation classes only when there is implementation-specific behavior not covered by the domain interface KDoc — for example: password hashing, guard clauses that throw, or side effects.

Do NOT duplicate the domain interface KDoc. A one-line `/** @see domain interface */` is sufficient for pure delegation.

## Gradle Commands
Never run Gradle commands automatically. Print the command for the user:
> Run the command `{command}` and let me know if there is any issue.

## Unit Tests

Tests live in `src/test/kotlin/` mirroring the source package path. Use **Mockito-Kotlin** with direct constructor instantiation — do not use `@ExtendWith`, `@Mock`, or `@InjectMocks`.

```kotlin
class CreateUserUseCaseTest {
    private val userRepository: UserRepositoryPort = mock()
    private val useCase = CreateUserUseCase(userRepository)

    @Test
    fun `invoke delegates to repository and returns result`() {
        val user = User(id = UUID.randomUUID(), email = "a@b.com")
        whenever(userRepository.createUser(user)).thenReturn(user)

        val result = useCase(user)

        assertEquals(user, result)
        verify(userRepository).createUser(user)
    }

    @Test
    fun `invoke returns null when repository returns null`() {
        val user = User(id = null, email = "a@b.com")
        whenever(userRepository.createUser(user)).thenReturn(null)

        assertNull(useCase(user))
    }
}
```

- Imports: `kotlin.test.Test`, `kotlin.test.assertEquals`, `kotlin.test.assertNull`, `kotlin.test.assertFailsWith`
- Mockito imports: `org.mockito.kotlin.mock`, `org.mockito.kotlin.whenever`, `org.mockito.kotlin.verify`
- Mock **port interfaces** from `:domain/port/`, never concrete adapter classes.
- One test class per use case. Cover: happy path, null/not-found return, and any guard clauses that throw.
- Run a single test class: `./gradlew :auth-service:application:test --tests "FullyQualifiedClassName"`
