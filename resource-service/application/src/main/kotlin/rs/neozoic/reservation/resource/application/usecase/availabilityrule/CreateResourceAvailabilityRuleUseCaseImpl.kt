package rs.neozoic.reservation.resource.application.usecase.availabilityrule

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.ResourceAvailabilityRule
import rs.neozoic.reservation.domain.port.ResourceAvailabilityRuleRepositoryPort
import rs.neozoic.reservation.domain.port.ResourceRepositoryPort
import rs.neozoic.reservation.domain.usecase.availabilityrule.CreateResourceAvailabilityRuleUseCase
import java.util.UUID

/**
 * Validates resource existence and time ordering before persisting the rule.
 * Throws [IllegalArgumentException] if the resource is not found or startTime >= endTime.
 */
@Service
class CreateResourceAvailabilityRuleUseCaseImpl(
    private val ruleRepository: ResourceAvailabilityRuleRepositoryPort,
    private val resourceRepository: ResourceRepositoryPort
) : CreateResourceAvailabilityRuleUseCase {
    override operator fun invoke(resourceId: UUID, rule: ResourceAvailabilityRule): ResourceAvailabilityRule {
        require(resourceRepository.existsByPublicId(resourceId)) { "Resource not found" }
        require(rule.startTime < rule.endTime) { "startTime must be before endTime" }

        return ruleRepository.createRule(rule)
    }
}
