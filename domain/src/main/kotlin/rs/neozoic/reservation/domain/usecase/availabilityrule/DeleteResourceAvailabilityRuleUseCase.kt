package rs.neozoic.reservation.domain.usecase.availabilityrule

import java.util.UUID

/**
 * Removes an availability rule from a resource.
 *
 * Deleting a rule does not cancel existing reservations that were booked under it;
 * only future booking validation is affected.
 *
 * @param ruleId public ID of the rule to delete.
 * @throws IllegalArgumentException if no rule with [ruleId] exists.
 */
interface DeleteResourceAvailabilityRuleUseCase {
    operator fun invoke(ruleId: UUID)
}
