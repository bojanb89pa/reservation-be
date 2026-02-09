package rs.neozoic.reservation.resource.data.repository

import org.springframework.data.jpa.repository.JpaRepository
import rs.neozoic.reservation.resource.data.model.entity.ReservationEntity
import java.util.*

interface ReservationRepository : JpaRepository<ReservationEntity, Long> {
    fun findByPublicId(publicId: UUID): ReservationEntity
}