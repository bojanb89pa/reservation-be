package rs.neozoic.reservation.domain.model

import java.util.*

data class User(
    val id: UUID?,
    val email: String,
    val roles: Set<Role>,
    var password: String? = null,
    var enabled: Boolean? = null
)
