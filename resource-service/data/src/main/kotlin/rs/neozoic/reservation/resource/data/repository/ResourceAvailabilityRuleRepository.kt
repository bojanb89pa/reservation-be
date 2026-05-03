package rs.neozoic.reservation.resource.data.repository

import org.springframework.data.jpa.repository.JpaRepository
import rs.neozoic.reservation.resource.data.model.entity.ResourceAvailabilityRuleEntity
import java.util.*

interface ResourceAvailabilityRuleRepository : JpaRepository<ResourceAvailabilityRuleEntity, Long> {
    fun findByPublicId(publicId: UUID): ResourceAvailabilityRuleEntity?
    fun findAllByResourcePublicId(resourcePublicId: UUID): List<ResourceAvailabilityRuleEntity>
    fun deleteByPublicId(publicId: UUID)
}
