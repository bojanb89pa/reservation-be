package rs.neozoic.reservation.domain.model

import java.time.LocalDateTime

data class Reservation(
    val id: String?,
    val user: User?,
    val business: Business?,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)
