package rs.neozoic.reservation.domain.usecase.user

import rs.neozoic.reservation.domain.model.User

interface CreateUserUseCase {
    operator fun invoke(user: User): User
}
