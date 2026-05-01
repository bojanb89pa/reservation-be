package rs.neozoic.reservation.auth.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.usecase.user.CreateUserUseCase
import rs.neozoic.reservation.domain.usecase.user.GetUserByEmailUseCase

@Tag(name = "Users", description = "User registration and lookup")
@RestController
@RequestMapping("/api/users")
class UserController(
    private val createUserUseCase: CreateUserUseCase,
    private val getUserByEmailUseCase: GetUserByEmailUseCase
) {

    @Operation(summary = "Register a new user")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "User registered successfully"),
        ApiResponse(responseCode = "500", description = "Email already registered")
    )
    @PostMapping
    fun createUser(@RequestBody user: User): ResponseEntity<User> =
        ResponseEntity.ok(createUserUseCase(user))

    @Operation(summary = "Look up a user by email")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "User found"),
        ApiResponse(responseCode = "404", description = "No user with the given email")
    )
    @GetMapping
    fun getUserByEmail(@RequestParam email: String): ResponseEntity<User> {
        val user = getUserByEmailUseCase(email)
        return user?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }
}
