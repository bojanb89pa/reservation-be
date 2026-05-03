package rs.neozoic.reservation.domain.model

import java.time.LocalDateTime
import java.util.UUID

/**
 * Represents a time-slot reservation made by a user for a specific resource.
 *
 * @property id public identifier; null before persistence.
 * @property userId public ID of the user who made the reservation.
 * @property resourceId public ID of the reserved resource; the resource's owning
 *   business is implicit through the resource.
 * @property startTime start of the reserved slot (inclusive).
 * @property endTime end of the reserved slot (exclusive).
 */
data class Reservation(
    val id: UUID?,
    val userId: UUID?,
    val resourceId: UUID,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)
