package rs.neozoic.reservation.resource.application.usecase.availabilityrule

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.port.ResourceAvailabilityRuleRepositoryPort
import rs.neozoic.reservation.domain.usecase.availabilityrule.DeleteResourceAvailabilityRuleUseCase
import java.util.UUID

/**
 * Validates rule existence before deletion.
 * Throws [IllegalArgumentException] if no rule with [ruleId] exists.
 */
@Service
class DeleteResourceAvailabilityRuleUseCaseImpl(
    private val ruleRepository: ResourceAvailabilityRuleRepositoryPort
) : DeleteResourceAvailabilityRuleUseCase {
    override operator fun invoke(ruleId: UUID) {
        requireNotNull(ruleRepository.getRuleByPublicId(ruleId)) { "Rule not found" }

        ruleRepository.deleteRule(ruleId)
    }
}
