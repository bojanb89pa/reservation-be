package rs.neozoic.reservation.resource.data.model.mapper

import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.resource.data.model.entity.BusinessEntity
import rs.neozoic.reservation.resource.data.model.entity.ReservationEntity
import java.util.*


fun ReservationEntity.toDomain() =
    Reservation(
        id = this.publicId,
        userId = this.userPublicId,
        startTime = this.startTime,
        endTime = this.endTime,
        businessId = this.business.publicId
    )

fun Reservation.toEntity(userId: UUID, business: BusinessEntity) =
    ReservationEntity(
        publicId = this.id ?: UUID.randomUUID(),
        userPublicId = userId,
        startTime = this.startTime,
        endTime = this.endTime,
        business = business,
    )