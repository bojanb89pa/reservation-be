package rs.neozoic.reservation.auth.data.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rs.neozoic.reservation.auth.data.model.entity.UserEntity

@Repository
interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): UserEntity?
}