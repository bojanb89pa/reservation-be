package rs.neozoic.reservation.domain.usecase.user

import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.model.UserRegistration

/**
 * Creates a new admin account with elevated privileges.
 *
 * The created account is assigned both [rs.neozoic.reservation.domain.model.Role.ROLE_USER] and
 * [rs.neozoic.reservation.domain.model.Role.ROLE_ADMIN] and is immediately enabled — no email
 * activation is required, as admin accounts are provisioned by an existing administrator.
 *
 * @param registration registration details; roles and enabled state are set by this use case, not the caller
 * @return the persisted, fully enabled admin user
 * @throws IllegalStateException if an account with the given email already exists
 */
interface CreateAdminUserUseCase {
    operator fun invoke(registration: UserRegistration): User
}
