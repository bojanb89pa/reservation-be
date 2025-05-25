package rs.neozoic.reservation.domain.model

import java.util.*

data class AuthenticatedUser(
    val id: UUID,
    val email: String,
    val roles: Set<Role>
)