package rs.neozoic.reservation.domain.model

/**
 * Authorization roles assigned to users.
 * Roles are embedded as JWT claims by auth-service and enforced by resource-service.
 */
enum class Role {
    ROLE_USER,
    ROLE_ADMIN
}
