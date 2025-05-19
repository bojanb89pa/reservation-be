package rs.neozoic.reservation.auth.application.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.service.UserService
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.repository.UserRepository

@Service
class UserServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
): UserService {
    override fun createUser(user: User): User {
        if (userRepository.existsByEmail(user.email)) {
            // TODO throw custom exception
            throw RuntimeException("User already exists")
        }

        user.password = passwordEncoder.encode(user.password).toString()

        return userRepository.save(user)
    }

    override fun getUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }
}