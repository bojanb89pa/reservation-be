# CLAUDE.md — auth-service:api

> Load this file + root `CLAUDE.md` when working in `auth-service/api`. Do not load other service or submodule files.

## Role

Inbound adapters for the auth service — REST controllers that receive HTTP requests, map them to domain arguments, and delegate to use case interfaces. No business logic here.

## Controllers in This Module

| Controller | Endpoints |
|---|---|
| `UserController` | `POST /api/users`, `GET /api/users?email=` |

Supporting:
- `OpenApiConfig` — SpringDoc/Swagger UI configuration.

## Controller Pattern

- Annotate with `@RestController` and `@RequestMapping`.
- Inject **use case interfaces from `:domain`** — never inject `:application` classes directly.
- Map incoming DTOs to domain model arguments before calling the use case.
- Map use case results to response DTOs before returning.
- Return `ResponseEntity<ResponseDto>` from every handler.
- No `if/else` business rules. No direct repository access.

```kotlin
@RestController
@RequestMapping("/api/users")
class UserController(
    private val createUserUseCase: CreateUserUseCase,
    private val getUserByEmailUseCase: GetUserByEmailUseCase
) {
    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<UserResponse> {
        val user = createUserUseCase(request.toDomain())
        return ResponseEntity.ok(user?.toResponse())
    }

    @GetMapping
    fun getUserByEmail(@RequestParam email: String): ResponseEntity<UserResponse> {
        val user = getUserByEmailUseCase(email)
        return user?.let { ResponseEntity.ok(it.toResponse()) } ?: ResponseEntity.notFound().build()
    }
}
```

## DTOs

- Define request and response `data class` DTOs in this module (not in `:domain`).
- Naming: `CreateUserRequest`, `UserResponse`, etc.
- Add mapper extension functions alongside the DTO or in a dedicated `mapper/` package: `CreateUserRequest.toDomain()`, `User.toResponse()`.

> **Note:** The existing codebase currently passes domain models directly as request/response bodies. New endpoints should use dedicated DTOs to avoid leaking domain internals over the wire.

## Validation

- Use Bean Validation annotations (`@NotBlank`, `@Email`, etc.) on request DTO fields.
- Annotate the controller parameter with `@Valid` to trigger validation.
- Do not write manual validation logic in the controller body.

## Swagger / OpenAPI Rules

Every controller class must have `@Tag(name = "...", description = "...")`.
Every handler method must have `@Operation(summary = "...")` — one short sentence, API-consumer oriented.
Add `@ApiResponse` only for non-obvious status codes (errors, 404, conflict).
The OAuth2 security scheme is declared globally in `OpenApiConfig` — do not repeat `@SecurityRequirement` on individual methods unless an endpoint deviates from the global scheme.

Keep annotations concise:
```kotlin
@Tag(name = "Users", description = "User registration and lookup")
@RestController
class UserController(...) {

    @Operation(summary = "Register a new user")
    @ApiResponses(ApiResponse(responseCode = "200", description = "User registered"),
                  ApiResponse(responseCode = "500", description = "Email already registered"))
    @PostMapping
    fun createUser(...): ResponseEntity<User>
}
```

**Never put** architecture explanations, business logic, or implementation details in Swagger annotations.

## Gradle Commands
Never run Gradle commands automatically. Print the command for the user:
> Run the command `{command}` and let me know if there is any issue.

## Unit Tests
`:api` controllers have no unit tests. Controller correctness (routing, mapping, validation) is best verified with Spring's `MockMvc` integration tests, which are not yet in this project. For now, verify controller behaviour manually via Swagger UI (`http://localhost:8081/swagger-ui/index.html`) or a REST client after running the service.

Do not unit-test controllers by mocking Spring internals — the overhead is not worth it for simple delegation code.

## OpenAPI

- `OpenApiConfig` configures the Swagger UI title, version, and security scheme.
- Use SpringDoc annotations (`@Operation`, `@ApiResponse`) on controller methods only when the generated docs are insufficient.
