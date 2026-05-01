package rs.neozoic.reservation.auth.application.usecase.user

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.port.UserRepositoryPort
import rs.neozoic.reservation.domain.usecase.user.GetUserByEmailUseCase as GetUserByEmailUseCasePort

@Service
class GetUserByEmailUseCase(
    private val userRepositoryPort: UserRepositoryPort
) : GetUserByEmailUseCasePort {
    override operator fun invoke(email: String): User? =
        userRepositoryPort.findByEmail(email)
}
