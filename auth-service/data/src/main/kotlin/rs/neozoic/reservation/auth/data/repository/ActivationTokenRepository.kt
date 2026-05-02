package rs.neozoic.reservation.auth.data.repository

import org.springframework.data.jpa.repository.JpaRepository
import rs.neozoic.reservation.auth.data.model.entity.ActivationTokenEntity

interface ActivationTokenRepository : JpaRepository<ActivationTokenEntity, Long> {
    fun findByToken(token: String): ActivationTokenEntity?
}
