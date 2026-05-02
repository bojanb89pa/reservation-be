package rs.neozoic.reservation.auth.application.usecase.user

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.PageRequest
import rs.neozoic.reservation.domain.model.PageResponse
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.port.UserRepositoryPort
import rs.neozoic.reservation.domain.usecase.user.GetAllUsersUseCase

@Service
class GetAllUsersUseCaseImpl(
    private val userRepositoryPort: UserRepositoryPort
) : GetAllUsersUseCase {
    override operator fun invoke(pageRequest: PageRequest): PageResponse<User> =
        userRepositoryPort.findAllUsers(pageRequest)
}
