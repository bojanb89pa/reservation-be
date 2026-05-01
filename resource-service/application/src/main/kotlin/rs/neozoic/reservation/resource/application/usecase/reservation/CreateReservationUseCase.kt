package rs.neozoic.reservation.resource.application.usecase.reservation

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.domain.port.BusinessRepositoryPort
import rs.neozoic.reservation.domain.port.ReservationRepositoryPort
import rs.neozoic.reservation.domain.usecase.reservation.CreateReservationUseCase as CreateReservationUseCasePort
import java.util.UUID

@Service
class CreateReservationUseCase(
    private val reservationRepository: ReservationRepositoryPort,
    private val businessRepository: BusinessRepositoryPort
) : CreateReservationUseCasePort {
    override operator fun invoke(userId: UUID, businessId: UUID, reservation: Reservation): Reservation {
        // TODO throw custom error
        require(businessRepository.existByPublicId(businessId)) { "Business not found" }

        return reservationRepository.createReservation(userId, businessId, reservation)
    }
}
