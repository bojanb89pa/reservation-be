package rs.neozoic.reservation.resource.data.repository

import org.springframework.stereotype.Repository
import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.domain.repository.ReservationRepository
import rs.neozoic.reservation.resource.data.model.mapper.toDomain
import rs.neozoic.reservation.resource.data.model.mapper.toEntity
import rs.neozoic.reservation.resource.data.repository.jpa.BusinessJpaRepository
import rs.neozoic.reservation.resource.data.repository.jpa.ReservationJpaRepository
import java.util.*

@Repository
class ReservationRepositoryImpl(
    private val reservationJpaRepository: ReservationJpaRepository,
    private val businessJpaRepository: BusinessJpaRepository
): ReservationRepository {
    override fun createReservation(userId: UUID, businessId: UUID, reservation: Reservation): Reservation {
        val business = businessJpaRepository.findByPublicId(businessId)
        return reservationJpaRepository.save(reservation.toEntity(userId = userId, business = business))
            .toDomain()
    }


    override fun getReservation(id: UUID): Reservation {
        TODO("Not yet implemented")
    }
}