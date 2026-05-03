package rs.neozoic.reservation.domain.usecase.availabilityrule

import rs.neozoic.reservation.domain.model.ResourceAvailabilityRule
import java.util.UUID

/**
 * Adds a recurring weekly availability window to a resource.
 *
 * The rule defines one day-of-week time window during which the resource can be booked.
 * Multiple rules can be created for a resource to express split shifts or multi-day schedules.
 *
 * @param resourceId public ID of the resource to configure.
 * @param rule the availability rule to persist.
 * @return the persisted [ResourceAvailabilityRule] with its assigned public ID.
 * @throws IllegalArgumentException if no resource with [resourceId] exists, or if
 *   [rule].startTime is not before [rule].endTime.
 */
interface CreateResourceAvailabilityRuleUseCase {
    operator fun invoke(resourceId: UUID, rule: ResourceAvailabilityRule): ResourceAvailabilityRule
}
