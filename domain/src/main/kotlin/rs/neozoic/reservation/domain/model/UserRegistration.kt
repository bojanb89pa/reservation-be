package rs.neozoic.reservation.domain.model

/**
 * Input model for user self-registration.
 *
 * Roles are intentionally excluded — the application layer assigns them based on the operation.
 *
 * @property email unique email address used as the login identifier
 * @property password plain-text password to be hashed by the application layer before persistence
 * @property firstName user's given name
 * @property lastName user's family name
 */
data class UserRegistration(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
)
