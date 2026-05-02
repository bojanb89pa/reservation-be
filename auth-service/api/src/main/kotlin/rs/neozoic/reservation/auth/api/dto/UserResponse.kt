package rs.neozoic.reservation.auth.api.dto

import rs.neozoic.reservation.domain.model.Role
import rs.neozoic.reservation.domain.model.User
import java.util.UUID

data class UserResponse(
    val id: UUID?,
    val email: String,
    val firstName: String,
    val lastName: String,
    val roles: Set<Role>,
    val enabled: Boolean?
)

fun User.toResponse() = UserResponse(
    id = id,
    email = email,
    firstName = firstName,
    lastName = lastName,
    roles = roles,
    enabled = enabled
)
