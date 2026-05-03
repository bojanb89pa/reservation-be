package rs.neozoic.reservation.domain.port

import rs.neozoic.reservation.domain.model.ResourceAvailabilityRule
import java.util.UUID

/** Outbound port for resource availability rule persistence operations. */
interface ResourceAvailabilityRuleRepositoryPort {
    fun createRule(rule: ResourceAvailabilityRule): ResourceAvailabilityRule
    fun getRuleByPublicId(publicId: UUID): ResourceAvailabilityRule?
    fun findAllByResourceId(resourceId: UUID): List<ResourceAvailabilityRule>
    fun deleteRule(publicId: UUID)
}
