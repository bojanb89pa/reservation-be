package rs.neozoic.reservation.domain.usecase.user

import rs.neozoic.reservation.domain.model.User

/**
 * Looks up a user by their email address.
 *
 * @return the matching [User], or null if no user with the given email exists.
 */
interface GetUserByEmailUseCase {
    operator fun invoke(email: String): User?
}
