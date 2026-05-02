package rs.neozoic.reservation.auth.application.usecase.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.Role
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.model.UserRegistration
import rs.neozoic.reservation.domain.port.UserRepositoryPort
import rs.neozoic.reservation.domain.usecase.user.CreateAdminUserUseCase

/** Hashes the password before persistence. The account is immediately enabled — no activation step. */
@Service
class CreateAdminUserUseCaseImpl(
    private val passwordEncoder: PasswordEncoder,
    private val userRepositoryPort: UserRepositoryPort
) : CreateAdminUserUseCase {
    override operator fun invoke(registration: UserRegistration): User {
        if (userRepositoryPort.existsByEmail(registration.email)) {
            throw IllegalStateException("Account with email ${registration.email} already exists")
        }

        val user = User(
            id = null,
            email = registration.email,
            roles = setOf(Role.ROLE_USER, Role.ROLE_ADMIN),
            firstName = registration.firstName,
            lastName = registration.lastName,
            password = passwordEncoder.encode(registration.password),
            enabled = true
        )

        return userRepositoryPort.save(user)
    }
}
