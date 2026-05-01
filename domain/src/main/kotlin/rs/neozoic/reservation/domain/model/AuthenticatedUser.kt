package rs.neozoic.reservation.domain.model

import java.util.*

/**
 * In-process representation of the currently authenticated principal.
 *
 * Populated by `JwtToUserConverter` on each request from JWT claims — not persisted.
 * Available in controllers via `@AuthenticationPrincipal`.
 */
data class AuthenticatedUser(
    val id: UUID,
    val email: String,
    val roles: Set<Role>
)
