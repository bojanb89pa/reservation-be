package rs.neozoic.reservation.resource.data.model.mapper

import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.resource.data.model.entity.BusinessEntity
import java.util.*

fun BusinessEntity.toDomain() =
    Business(
        id = this.publicId,
        name = this.name
    )

fun Business.toEntity() =
    BusinessEntity(
        publicId = this.id ?: UUID.randomUUID(),
        name = this.name
    )