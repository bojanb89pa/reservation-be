package rs.neozoic.reservation

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import rs.neozoic.reservation.data.model.entity.UserEntity
import rs.neozoic.reservation.data.repository.jpa.UserJpaRepository
import rs.neozoic.reservation.domain.dto.Role
import rs.neozoic.reservation.domain.dto.User
import java.util.*

@Service
class UserService(
    private val passwordEncoder: PasswordEncoder,
    private val userJpaRepository: UserJpaRepository
) {

    fun createUser(user: User): User {

        if (userJpaRepository.existsByEmail(user.email)) {
            // TODO throw custom exception
            throw RuntimeException("User already exists")
        }
        val userEntity = UserEntity(
            publicId = UUID.randomUUID(),
            email = user.email,
            password = passwordEncoder.encode(user.password),
            activated = false,
            enabled = true,
            roles = setOf(Role.ROLE_USER)
        )
        val saved = userJpaRepository.save(userEntity)
        // TODO use mapper
        return User(id = saved.publicId.toString(), email = saved.email)
    }

    fun getUserByEmail(email: String): User? {
        val user = userJpaRepository.findByEmail(email)
        return user?.let {
            User(id = it.publicId.toString(), email = it.email)
        }
    }
}