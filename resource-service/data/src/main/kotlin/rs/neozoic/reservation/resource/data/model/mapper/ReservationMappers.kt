package rs.neozoic.reservation.resource.data.model.mapper

import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.resource.data.model.entity.ReservationEntity
import rs.neozoic.reservation.resource.data.model.entity.ResourceEntity
import java.util.*

fun ReservationEntity.toDomain() =
    Reservation(
        id = this.publicId,
        userId = this.userPublicId,
        resourceId = this.resource.publicId,
        startTime = this.startTime,
        endTime = this.endTime
    )

fun Reservation.toEntity(userId: UUID, resource: ResourceEntity) =
    ReservationEntity(
        publicId = this.id ?: UUID.randomUUID(),
        userPublicId = userId,
        resource = resource,
        startTime = this.startTime,
        endTime = this.endTime,
    )
