package rs.neozoic.reservation.auth.application.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.service.UserService
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.port.UserRepositoryPort

@Service
class UserServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val userRepositoryPort: UserRepositoryPort
): UserService {
    override fun createUser(user: User): User {
        if (userRepositoryPort.existsByEmail(user.email)) {
            // TODO throw custom exception
            throw RuntimeException("User already exists")
        }

        user.password = passwordEncoder.encode(user.password).toString()

        return userRepositoryPort.save(user)
    }

    override fun getUserByEmail(email: String): User? {
        return userRepositoryPort.findByEmail(email)
    }
}