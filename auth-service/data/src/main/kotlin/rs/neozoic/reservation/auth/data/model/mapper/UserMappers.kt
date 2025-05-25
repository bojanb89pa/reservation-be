package rs.neozoic.reservation.auth.data.model.mapper

import rs.neozoic.reservation.auth.data.model.entity.UserEntity
import rs.neozoic.reservation.domain.model.User
import java.util.*


fun UserEntity.toDomain(): User = User(
    id = this.publicId,
    email = this.email,
    roles = this.roles,
    firstName = this.firstName,
    lastName = this.lastName
)


fun UserEntity.toDomainInternal(): User = User(
    id = this.publicId,
    email = this.email,
    roles = this.roles,
    firstName = this.firstName,
    lastName = this.lastName,
    password = this.password,
    enabled = this.enabled
)

fun User.toEntity(): UserEntity = UserEntity(
    id = null,
    publicId = this.id ?: UUID.randomUUID(),
    email = this.email,
    firstName = this.firstName,
    lastName = this.lastName,
    password = this.password,
    enabled = true,
    activated = false,
    roles = this.roles
)