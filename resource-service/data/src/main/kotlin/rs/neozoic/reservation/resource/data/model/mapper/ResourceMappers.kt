package rs.neozoic.reservation.resource.data.model.mapper

import rs.neozoic.reservation.domain.model.Resource
import rs.neozoic.reservation.resource.data.model.entity.BusinessEntity
import rs.neozoic.reservation.resource.data.model.entity.ResourceEntity
import java.util.*

fun ResourceEntity.toDomain() =
    Resource(
        id = this.publicId,
        businessId = this.business.publicId,
        type = this.type,
        name = this.name
    )

fun Resource.toEntity(business: BusinessEntity) =
    ResourceEntity(
        publicId = this.id ?: UUID.randomUUID(),
        business = business,
        type = this.type,
        name = this.name
    )
