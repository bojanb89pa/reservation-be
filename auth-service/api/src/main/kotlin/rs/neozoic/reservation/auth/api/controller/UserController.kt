package rs.neozoic.reservation.auth.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import rs.neozoic.reservation.auth.api.dto.RegisterUserRequest
import rs.neozoic.reservation.auth.api.dto.UserResponse
import rs.neozoic.reservation.auth.api.dto.toResponse
import rs.neozoic.reservation.domain.usecase.user.ActivateUserUseCase
import rs.neozoic.reservation.domain.usecase.user.CreateAdminUserUseCase
import rs.neozoic.reservation.domain.usecase.user.GetUserByEmailUseCase
import rs.neozoic.reservation.domain.usecase.user.RegisterUserUseCase

@Tag(name = "Users", description = "User registration, admin provisioning, and account activation")
@RestController
@RequestMapping("/api/users")
class UserController(
    private val registerUserUseCase: RegisterUserUseCase,
    private val createAdminUserUseCase: CreateAdminUserUseCase,
    private val activateUserUseCase: ActivateUserUseCase,
    private val getUserByEmailUseCase: GetUserByEmailUseCase
) {

    @Operation(summary = "Register a new user account; an activation email is sent to the provided address")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "User registered; activation email sent"),
        ApiResponse(responseCode = "400", description = "Invalid request body"),
        ApiResponse(responseCode = "409", description = "Email already registered")
    )
    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterUserRequest): ResponseEntity<UserResponse> =
        ResponseEntity.ok(registerUserUseCase(request.toDomain()).toResponse())

    @Operation(summary = "Provision a new admin account (immediately enabled, no activation required)")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Admin account created"),
        ApiResponse(responseCode = "400", description = "Invalid request body"),
        ApiResponse(responseCode = "409", description = "Email already registered")
    )
    @PostMapping("/admin")
    fun createAdmin(@Valid @RequestBody request: RegisterUserRequest): ResponseEntity<UserResponse> =
        ResponseEntity.ok(createAdminUserUseCase(request.toDomain()).toResponse())

    @Operation(summary = "Activate a user account using the token from the activation email")
    @ApiResponses(
        ApiResponse(responseCode = "302", description = "Redirects to /login — activated=true on success, error=activation_failed or error=activation_expired on failure")
    )
    @GetMapping("/activate")
    fun activate(@RequestParam token: String): ResponseEntity<Void> {
        return try {
            activateUserUseCase(token)
            ResponseEntity.status(302).location(URI.create("/login?activated=true")).build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(302).location(URI.create("/login?error=activation_failed")).build()
        } catch (e: IllegalStateException) {
            val errorParam = if (e.message?.contains("expired") == true) "activation_expired" else "activation_failed"
            ResponseEntity.status(302).location(URI.create("/login?error=$errorParam")).build()
        }
    }

    @Operation(summary = "Look up a user by email")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "User found"),
        ApiResponse(responseCode = "404", description = "No user with the given email")
    )
    @GetMapping
    fun getUserByEmail(@RequestParam email: String): ResponseEntity<UserResponse> {
        val user = getUserByEmailUseCase(email)
        return user?.let { ResponseEntity.ok(it.toResponse()) } ?: ResponseEntity.notFound().build()
    }
}
