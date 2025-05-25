package rs.neozoic.reservation.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class Reservation(
    val id: UUID?,
    val userId: UUID?,
    val business: Business?,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)
