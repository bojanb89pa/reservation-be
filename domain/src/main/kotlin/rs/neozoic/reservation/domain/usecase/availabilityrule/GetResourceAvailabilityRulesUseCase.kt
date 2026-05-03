package rs.neozoic.reservation.domain.usecase.availabilityrule

import rs.neozoic.reservation.domain.model.ResourceAvailabilityRule
import java.util.UUID

/**
 * Returns all availability rules defined for a resource, ordered by day of week then start time.
 *
 * An empty list means the resource has no defined availability and cannot be booked.
 *
 * @param resourceId public ID of the resource.
 * @return all [ResourceAvailabilityRule] entries for the resource; never null, may be empty.
 * @throws IllegalArgumentException if no resource with [resourceId] exists.
 */
interface GetResourceAvailabilityRulesUseCase {
    operator fun invoke(resourceId: UUID): List<ResourceAvailabilityRule>
}
