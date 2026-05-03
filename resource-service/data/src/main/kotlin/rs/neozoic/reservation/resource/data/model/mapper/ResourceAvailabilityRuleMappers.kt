package rs.neozoic.reservation.resource.data.model.mapper

import rs.neozoic.reservation.domain.model.ResourceAvailabilityRule
import rs.neozoic.reservation.resource.data.model.entity.ResourceAvailabilityRuleEntity
import rs.neozoic.reservation.resource.data.model.entity.ResourceEntity
import java.util.*

fun ResourceAvailabilityRuleEntity.toDomain() =
    ResourceAvailabilityRule(
        id = this.publicId,
        resourceId = this.resource.publicId,
        dayOfWeek = this.dayOfWeek,
        startTime = this.startTime,
        endTime = this.endTime
    )

fun ResourceAvailabilityRule.toEntity(resource: ResourceEntity) =
    ResourceAvailabilityRuleEntity(
        publicId = this.id ?: UUID.randomUUID(),
        resource = resource,
        dayOfWeek = this.dayOfWeek,
        startTime = this.startTime,
        endTime = this.endTime
    )
