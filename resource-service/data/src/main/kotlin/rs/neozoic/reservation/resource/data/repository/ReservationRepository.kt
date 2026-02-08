package rs.neozoic.reservation.resource.data.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rs.neozoic.reservation.resource.data.model.entity.ReservationEntity
import java.util.*

@Repository
interface ReservationRepository : JpaRepository<ReservationEntity, Long> {
    fun findByPublicId(publicId: UUID): ReservationEntity
}