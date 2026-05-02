package rs.neozoic.reservation.auth.application.usecase.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.ActivationToken
import rs.neozoic.reservation.domain.model.Role
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.model.UserRegistration
import rs.neozoic.reservation.domain.port.ActivationTokenRepositoryPort
import rs.neozoic.reservation.domain.port.UserActivationEmailPort
import rs.neozoic.reservation.domain.port.UserRepositoryPort
import rs.neozoic.reservation.domain.usecase.user.RegisterUserUseCase
import java.time.LocalDateTime
import java.util.UUID

/**
 * Hashes the password before persistence, creates a 24-hour activation token, and
 * triggers the activation email. The account is left disabled until the token is redeemed.
 */
@Service
class RegisterUserUseCaseImpl(
    private val passwordEncoder: PasswordEncoder,
    private val userRepositoryPort: UserRepositoryPort,
    private val activationTokenRepositoryPort: ActivationTokenRepositoryPort,
    private val userActivationEmailPort: UserActivationEmailPort
) : RegisterUserUseCase {
    override operator fun invoke(registration: UserRegistration): User {
        if (userRepositoryPort.existsByEmail(registration.email)) {
            throw IllegalStateException("Account with email ${registration.email} already exists")
        }

        val user = User(
            id = null,
            email = registration.email,
            roles = setOf(Role.ROLE_USER),
            firstName = registration.firstName,
            lastName = registration.lastName,
            password = passwordEncoder.encode(registration.password),
            enabled = false
        )

        val savedUser = userRepositoryPort.save(user)

        val activationToken = ActivationToken(
            id = null,
            userId = savedUser.id!!,
            token = UUID.randomUUID().toString(),
            expiresAt = LocalDateTime.now().plusHours(24)
        )

        val savedToken = activationTokenRepositoryPort.save(activationToken)

        userActivationEmailPort.sendActivationEmail(savedUser, savedToken.token)

        return savedUser
    }
}
