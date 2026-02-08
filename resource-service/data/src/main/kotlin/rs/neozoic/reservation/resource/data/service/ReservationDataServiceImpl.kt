package rs.neozoic.reservation.resource.data.service

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.domain.service.data.ReservationDataService
import rs.neozoic.reservation.resource.data.model.mapper.toDomain
import rs.neozoic.reservation.resource.data.model.mapper.toEntity
import rs.neozoic.reservation.resource.data.repository.BusinessRepository
import rs.neozoic.reservation.resource.data.repository.ReservationRepository
import java.util.*

@Service
class ReservationDataServiceImpl(
    private val reservationRepository: ReservationRepository,
    private val businessRepository: BusinessRepository
): ReservationDataService {
    override fun createReservation(userId: UUID, businessId: UUID, reservation: Reservation): Reservation {
        val business = businessRepository.findByPublicId(businessId)
        return reservationRepository.save(reservation.toEntity(userId = userId, business = business))
            .toDomain()
    }


    override fun getReservation(id: UUID): Reservation =
        reservationRepository.findByPublicId(id).toDomain()
}