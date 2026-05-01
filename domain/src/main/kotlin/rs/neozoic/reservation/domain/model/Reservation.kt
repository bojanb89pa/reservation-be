package rs.neozoic.reservation.domain.model

import java.time.LocalDateTime
import java.util.UUID

/**
 * Represents a time-slot reservation made by a user at a business.
 *
 * @property id public identifier; null before persistence.
 * @property userId public ID of the user who made the reservation.
 * @property businessId public ID of the target business.
 * @property startTime start of the reserved slot (inclusive).
 * @property endTime end of the reserved slot (exclusive).
 */
data class Reservation(
    val id: UUID?,
    val userId: UUID?,
    val businessId: UUID?,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)
