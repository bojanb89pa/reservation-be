package rs.neozoic.reservation.auth.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.usecase.user.CreateUserUseCase
import rs.neozoic.reservation.domain.usecase.user.GetUserByEmailUseCase

@RestController
@RequestMapping("/api/users")
class UserController(
    private val createUserUseCase: CreateUserUseCase,
    private val getUserByEmailUseCase: GetUserByEmailUseCase
) {
    @PostMapping
    fun createUser(@RequestBody user: User): ResponseEntity<User> =
        ResponseEntity.ok(createUserUseCase(user))

    @GetMapping
    fun getUserByEmail(@RequestParam email: String): ResponseEntity<User> {
        val user = getUserByEmailUseCase(email)
        return user?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }
}
