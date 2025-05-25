package rs.neozoic.reservation.resource.application.service

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.domain.repository.BusinessRepository
import rs.neozoic.reservation.domain.repository.ReservationRepository
import rs.neozoic.reservation.domain.service.ReservationService
import java.util.*

@Service
class ReservationServiceImpl(
    private val reservationRepository: ReservationRepository,
    private val businessRepository: BusinessRepository
) : ReservationService {
    override fun createReservation(userId: UUID, businessId: UUID, reservation: Reservation): Reservation {
        // TODO throw custom error
        require(businessRepository.existByPublicId(businessId)) { "Business not found" }

        return reservationRepository.createReservation(userId, businessId, reservation)
    }

    override fun getReservation(id: UUID): Reservation =
        reservationRepository.getReservation(id)

}