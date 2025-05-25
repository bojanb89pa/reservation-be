package rs.neozoic.reservation.resource.data.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository
import rs.neozoic.reservation.resource.data.model.entity.ReservationEntity
import java.util.*

interface ReservationJpaRepository : JpaRepository<ReservationEntity, Long> {
    fun findByPublicId(publicId: UUID): ReservationEntity
}