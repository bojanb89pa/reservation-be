package rs.neozoic.reservation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import rs.neozoic.reservation.domain.dto.User

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        return ResponseEntity.ok(userService.createUser(user))
    }

    @GetMapping
    fun getUserByEmail(@RequestParam email: String): ResponseEntity<User> {
        val user = userService.getUserByEmail(email)
        return user?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

}