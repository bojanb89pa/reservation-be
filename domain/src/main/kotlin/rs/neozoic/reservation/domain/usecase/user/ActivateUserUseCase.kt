package rs.neozoic.reservation.domain.usecase.user

import rs.neozoic.reservation.domain.model.User

/**
 * Activates a user account by redeeming a one-time email activation token.
 *
 * On success the user's account is enabled and the token is marked as used so it cannot be
 * redeemed again.
 *
 * @param token the raw token string extracted from the activation link
 * @return the activated user with [User.enabled] set to `true`
 * @throws IllegalArgumentException if no token matching the given value exists
 * @throws IllegalStateException if the token has already been used or has expired
 */
interface ActivateUserUseCase {
    operator fun invoke(token: String): User
}
