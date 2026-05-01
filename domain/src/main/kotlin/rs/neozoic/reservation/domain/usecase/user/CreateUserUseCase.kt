package rs.neozoic.reservation.domain.usecase.user

import rs.neozoic.reservation.domain.model.User

/**
 * Registers a new user account.
 *
 * The password is hashed before persistence and is not returned in plain text.
 *
 * @return the persisted [User].
 * @throws RuntimeException if a user with the same email already exists.
 */
interface CreateUserUseCase {
    operator fun invoke(user: User): User
}
