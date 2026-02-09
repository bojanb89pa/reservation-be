package rs.neozoic.reservation.auth.data.repository

import org.springframework.data.jpa.repository.JpaRepository
import rs.neozoic.reservation.auth.data.model.entity.UserEntity

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): UserEntity?
}