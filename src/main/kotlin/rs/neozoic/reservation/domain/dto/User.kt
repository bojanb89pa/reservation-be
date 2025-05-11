package rs.neozoic.reservation.domain.dto

data class User(
    val id: String?,
    val email: String,
    val password: String? = null,
)
