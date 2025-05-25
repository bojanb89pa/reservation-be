package rs.neozoic.reservation.resource.data.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository
import rs.neozoic.reservation.resource.data.model.entity.ReservationEntity

interface ReservationJpaRepository : JpaRepository<ReservationEntity, Long> {
}