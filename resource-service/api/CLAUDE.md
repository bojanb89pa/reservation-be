# CLAUDE.md — resource-service:api

> Load this file + root `CLAUDE.md` when working in `resource-service/api`. Do not load other service or submodule files.

## Role

Inbound adapters for the resource service — REST controllers that receive HTTP requests, map them to domain arguments, and delegate to use case interfaces. No business logic here.

## Controllers in This Module

| Controller | Endpoints |
|---|---|
| `BusinessController` | `POST /api/businesses`, `GET /api/businesses/{id}` |
| `ReservationController` | `POST /api/reservations`, `GET /api/reservations/{id}` |

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
@RequestMapping("/api/businesses")
class BusinessController(
    private val createBusinessUseCase: CreateBusinessUseCase,
    private val getBusinessUseCase: GetBusinessUseCase
) {
    @PostMapping
    fun createBusiness(@RequestBody request: CreateBusinessRequest): ResponseEntity<BusinessResponse> {
        val business = createBusinessUseCase(request.toDomain())
        return ResponseEntity.ok(business?.toResponse())
    }

    @GetMapping("/{id}")
    fun getBusiness(@PathVariable id: UUID): ResponseEntity<BusinessResponse> {
        val business = getBusinessUseCase(id)
        return business?.let { ResponseEntity.ok(it.toResponse()) } ?: ResponseEntity.notFound().build()
    }
}
```

## DTOs

- Define request and response `data class` DTOs in this module (not in `:domain`).
- Naming: `CreateBusinessRequest`, `BusinessResponse`, `CreateReservationRequest`, `ReservationResponse`, etc.
- Add mapper extension functions alongside the DTO or in a dedicated `mapper/` package: `CreateBusinessRequest.toDomain()`, `Business.toResponse()`.

> **Note:** The existing codebase currently passes domain models directly as request/response bodies. New endpoints should use dedicated DTOs to avoid leaking domain internals over the wire.

## Authentication Context

- Controllers may access the authenticated principal via `@AuthenticationPrincipal AuthenticatedUser user`.
- `AuthenticatedUser` is the domain model from `:domain/model/` — populated by `JwtToUserConverter` in the service root.

## Validation

- Use Bean Validation annotations (`@NotBlank`, `@Size`, etc.) on request DTO fields.
- Annotate the controller parameter with `@Valid` to trigger validation.
- Do not write manual validation logic in the controller body.

## Swagger / OpenAPI Rules

Every controller class must have `@Tag(name = "...", description = "...")`.
Every handler method must have `@Operation(summary = "...")` — one short sentence, API-consumer oriented.
Add `@ApiResponse` only for non-obvious status codes (errors, 404, conflict).
The OAuth2 security scheme is declared globally in `OpenApiConfig` — do not repeat `@SecurityRequirement` on individual methods unless an endpoint deviates from the global scheme.

Keep annotations concise:
```kotlin
@Tag(name = "Businesses", description = "Business management")
@RestController
class BusinessController(...) {

    @Operation(summary = "Create a new business")
    @PostMapping
    fun createBusiness(...): ResponseEntity<Business>
}
```

**Never put** architecture explanations, business logic, or implementation details in Swagger annotations.

## Gradle Commands
Never run Gradle commands automatically. Print the command for the user:
> Run the command `{command}` and let me know if there is any issue.

## Unit Tests
`:api` controllers have no unit tests. Controller correctness (routing, mapping, validation) is best verified with Spring's `MockMvc` integration tests, which are not yet in this project. For now, verify controller behaviour manually via Swagger UI (`http://localhost:8080/swagger-ui/index.html`) or a REST client after running the service.

Do not unit-test controllers by mocking Spring internals — the overhead is not worth it for simple delegation code.

## OpenAPI

- `OpenApiConfig` configures the Swagger UI title, version, and Bearer token security scheme.
- Use SpringDoc annotations (`@Operation`, `@ApiResponse`) on controller methods only when the generated docs are insufficient.
