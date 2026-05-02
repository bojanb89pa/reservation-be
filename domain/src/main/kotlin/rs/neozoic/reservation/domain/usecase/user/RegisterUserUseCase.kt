package rs.neozoic.reservation.domain.usecase.user

import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.model.UserRegistration

/**
 * Registers a new end-user account.
 *
 * The created account is assigned [rs.neozoic.reservation.domain.model.Role.ROLE_USER] and left
 * disabled until the user confirms their email address via the activation link.
 *
 * @param registration registration details; roles and enabled state are set by this use case, not the caller
 * @return the persisted user with [User.enabled] set to `false`
 * @throws IllegalStateException if an account with the given email already exists
 */
interface RegisterUserUseCase {
    operator fun invoke(registration: UserRegistration): User
}
