package rs.neozoic.reservation.auth.application.usecase.user

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.port.UserRepositoryPort
import rs.neozoic.reservation.domain.usecase.user.GetUserByEmailUseCase

@Service
class GetUserByEmailUseCaseImpl(
    private val userRepositoryPort: UserRepositoryPort
) : GetUserByEmailUseCase {
    override operator fun invoke(email: String): User? =
        userRepositoryPort.findByEmail(email)
}
