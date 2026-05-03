package rs.neozoic.reservation.domain.model

/** Classifies the kind of entity that can be reserved within a business. */
enum class ResourceType {
    EMPLOYEE,
    ROOM,
    APARTMENT,
    TABLE,
    COURT,
    VEHICLE
}
