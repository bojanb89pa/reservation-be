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

## Testing

Unit tests live in `src/test/kotlin/`. Use JUnit 5 + Mockito-Kotlin. Mock the port interface, never the adapter.

```kotlin
@ExtendWith(MockitoExtension::class)
class CreateUserUseCaseTest {
    @Mock lateinit var userRepository: UserRepositoryPort
    @InjectMocks lateinit var useCase: CreateUserUseCase

    @Test
    fun `invoke delegates to repository`() { ... }
}
```
