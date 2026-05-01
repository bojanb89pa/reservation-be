package rs.neozoic.reservation.auth.application.usecase.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.port.UserRepositoryPort
import rs.neozoic.reservation.domain.usecase.user.CreateUserUseCase as CreateUserUseCasePort

/**
 * Encodes the user's password before persistence and guards against duplicate email registration.
 * TODO: replace RuntimeException with a typed domain exception.
 */
@Service
class CreateUserUseCase(
    private val passwordEncoder: PasswordEncoder,
    private val userRepositoryPort: UserRepositoryPort
) : CreateUserUseCasePort {
    override operator fun invoke(user: User): User {
        if (userRepositoryPort.existsByEmail(user.email)) {
            throw RuntimeException("User already exists")
        }

        user.password = passwordEncoder.encode(user.password).toString()

        return userRepositoryPort.save(user)
    }
}
