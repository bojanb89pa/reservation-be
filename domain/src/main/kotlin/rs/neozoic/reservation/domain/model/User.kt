package rs.neozoic.reservation.domain.model

data class User(
    val id: String?,
    val email: String,
    val roles: Set<Role>,
    val password: String? = null,
)
