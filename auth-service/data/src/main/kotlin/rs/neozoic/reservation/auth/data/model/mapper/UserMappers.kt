package rs.neozoic.reservation.auth.data.model.mapper

import rs.neozoic.reservation.auth.data.model.entity.UserEntity
import rs.neozoic.reservation.domain.model.User
import java.util.*


fun UserEntity.toDomain(): User = User(
    id = this.publicId.toString(),
    email = this.email,
    roles = this.roles
)

fun User.toEntity(): UserEntity = UserEntity(
    id = null,
    publicId = this.id?.let {
        UUID.fromString(it)
    } ?: UUID.randomUUID(),
    email = this.email,
    password = this.password!!, // TODO use password encoder
    enabled = true,
    activated = false,
    roles = this.roles
)