package rs.neozoic.reservation.auth.application.usecase.user

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.port.ActivationTokenRepositoryPort
import rs.neozoic.reservation.domain.port.UserRepositoryPort
import rs.neozoic.reservation.domain.usecase.user.ActivateUserUseCase
import java.time.LocalDateTime

@Service
class ActivateUserUseCaseImpl(
    private val activationTokenRepositoryPort: ActivationTokenRepositoryPort,
    private val userRepositoryPort: UserRepositoryPort
) : ActivateUserUseCase {
    override operator fun invoke(token: String): User {
        val activationToken = activationTokenRepositoryPort.findByToken(token)
            ?: throw IllegalArgumentException("Activation token not found")

        if (activationToken.used) {
            throw IllegalStateException("Activation token has already been used")
        }

        if (activationToken.expiresAt.isBefore(LocalDateTime.now())) {
            throw IllegalStateException("Activation token has expired")
        }

        activationTokenRepositoryPort.markAsUsed(token)

        return userRepositoryPort.activateUser(activationToken.userId)
            ?: throw IllegalStateException("User not found for activation token")
    }
}
