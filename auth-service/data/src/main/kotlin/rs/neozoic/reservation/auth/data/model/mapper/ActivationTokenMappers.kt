package rs.neozoic.reservation.auth.data.model.mapper

import rs.neozoic.reservation.auth.data.model.entity.ActivationTokenEntity
import rs.neozoic.reservation.domain.model.ActivationToken
import java.util.*

fun ActivationTokenEntity.toDomain() = ActivationToken(
    id = this.publicId,
    userId = this.userId,
    token = this.token,
    expiresAt = this.expiresAt,
    used = this.used
)

fun ActivationToken.toEntity() = ActivationTokenEntity(
    publicId = this.id ?: UUID.randomUUID(),
    userId = this.userId,
    token = this.token,
    expiresAt = this.expiresAt,
    used = this.used
)
