package rs.neozoic.reservation.domain.usecase.user

import rs.neozoic.reservation.domain.model.User

interface GetUserByEmailUseCase {
    operator fun invoke(email: String): User?
}
