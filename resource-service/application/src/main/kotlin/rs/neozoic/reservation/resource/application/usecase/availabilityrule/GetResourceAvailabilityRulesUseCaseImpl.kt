package rs.neozoic.reservation.resource.application.usecase.availabilityrule

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.ResourceAvailabilityRule
import rs.neozoic.reservation.domain.port.ResourceAvailabilityRuleRepositoryPort
import rs.neozoic.reservation.domain.port.ResourceRepositoryPort
import rs.neozoic.reservation.domain.usecase.availabilityrule.GetResourceAvailabilityRulesUseCase
import java.util.UUID

/**
 * Validates resource existence before querying rules.
 * Throws [IllegalArgumentException] if the resource is not found.
 */
@Service
class GetResourceAvailabilityRulesUseCaseImpl(
    private val ruleRepository: ResourceAvailabilityRuleRepositoryPort,
    private val resourceRepository: ResourceRepositoryPort
) : GetResourceAvailabilityRulesUseCase {
    override operator fun invoke(resourceId: UUID): List<ResourceAvailabilityRule> {
        require(resourceRepository.existsByPublicId(resourceId)) { "Resource not found" }

        return ruleRepository.findAllByResourceId(resourceId)
    }
}
