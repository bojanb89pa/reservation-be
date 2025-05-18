package rs.neozoic.reservation.auth.application.impl

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.service.UserService
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.repository.UserRepository

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {
    override fun createUser(user: User): User {
        if (userRepository.existsByEmail(user.email)) {
            // TODO throw custom exception
            throw RuntimeException("User already exists")
        }
        return userRepository.save(user)
    }

    override fun getUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }
}