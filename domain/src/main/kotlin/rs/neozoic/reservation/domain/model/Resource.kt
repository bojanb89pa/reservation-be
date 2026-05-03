package rs.neozoic.reservation.domain.model

import java.util.UUID

/**
 * A reservable resource owned by a business.
 *
 * @property id public identifier; null before persistence.
 * @property businessId public ID of the owning business.
 * @property type the category of this resource (e.g. EMPLOYEE, ROOM).
 * @property name human-readable label shown to customers (e.g. "John – Hair Stylist", "Room 3").
 */
data class Resource(
    val id: UUID?,
    val businessId: UUID,
    val type: ResourceType,
    val name: String
)
