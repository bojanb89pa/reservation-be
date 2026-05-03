package rs.neozoic.reservation.resource.data.repository.adapter

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import rs.neozoic.reservation.domain.model.ResourceAvailabilityRule
import rs.neozoic.reservation.domain.port.ResourceAvailabilityRuleRepositoryPort
import rs.neozoic.reservation.resource.data.model.mapper.toDomain
import rs.neozoic.reservation.resource.data.model.mapper.toEntity
import rs.neozoic.reservation.resource.data.repository.ResourceAvailabilityRuleRepository
import rs.neozoic.reservation.resource.data.repository.ResourceRepository
import java.util.*

@Repository
class ResourceAvailabilityRuleRepositoryAdapter(
    private val ruleRepository: ResourceAvailabilityRuleRepository,
    private val resourceRepository: ResourceRepository
) : ResourceAvailabilityRuleRepositoryPort {
    override fun createRule(rule: ResourceAvailabilityRule): ResourceAvailabilityRule {
        val resource = resourceRepository.findByPublicId(rule.resourceId)!!
        return ruleRepository.save(rule.toEntity(resource)).toDomain()
    }

    override fun getRuleByPublicId(publicId: UUID): ResourceAvailabilityRule? =
        ruleRepository.findByPublicId(publicId)?.toDomain()

    override fun findAllByResourceId(resourceId: UUID): List<ResourceAvailabilityRule> =
        ruleRepository.findAllByResourcePublicId(resourceId).map { it.toDomain() }

    @Transactional
    override fun deleteRule(publicId: UUID) =
        ruleRepository.deleteByPublicId(publicId)
}
