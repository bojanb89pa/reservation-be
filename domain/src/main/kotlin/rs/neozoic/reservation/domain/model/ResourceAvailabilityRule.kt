package rs.neozoic.reservation.domain.model

import java.time.DayOfWeek
import java.time.LocalTime
import java.util.UUID

/**
 * Defines a recurring weekly availability window for a resource.
 *
 * A rule states that the resource is bookable on a given [dayOfWeek] between
 * [startTime] (inclusive) and [endTime] (exclusive). Multiple rules can exist
 * for the same resource to express split shifts or multi-day schedules.
 * Reservations that fall outside all rules for a resource must be rejected.
 *
 * @property id public identifier; null before persistence.
 * @property resourceId public ID of the resource this rule governs.
 * @property dayOfWeek the weekday on which this window applies.
 * @property startTime the earliest time a reservation may begin on this day (inclusive).
 * @property endTime the latest time a reservation may end on this day (exclusive).
 */
data class ResourceAvailabilityRule(
    val id: UUID?,
    val resourceId: UUID,
    val dayOfWeek: DayOfWeek,
    val startTime: LocalTime,
    val endTime: LocalTime
)
