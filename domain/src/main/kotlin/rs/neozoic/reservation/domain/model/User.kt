package rs.neozoic.reservation.domain.model

import java.util.*

/**
 * Represents an application user.
 *
 * @property id public identifier; null before persistence.
 * @property email unique email address used for authentication and lookup.
 * @property roles set of roles that control authorization across services.
 * @property firstName user's first name.
 * @property lastName user's last name.
 * @property password hashed password; null when returned from non-authentication operations.
 * @property enabled whether the account is active; null means not explicitly set.
 */
data class User(
    val id: UUID?,
    val email: String,
    val roles: Set<Role>,
    val firstName: String,
    val lastName: String,
    var password: String? = null,
    var enabled: Boolean? = null
)
