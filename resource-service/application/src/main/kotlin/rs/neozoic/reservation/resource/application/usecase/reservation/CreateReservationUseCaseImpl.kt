package rs.neozoic.reservation.resource.application.usecase.reservation

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.domain.port.BusinessRepositoryPort
import rs.neozoic.reservation.domain.port.ReservationRepositoryPort
import rs.neozoic.reservation.domain.usecase.reservation.CreateReservationUseCase
import java.util.UUID

/**
 * Validates business existence before delegating to the reservation repository.
 * Throws [IllegalArgumentException] if the business is not found.
 * TODO: replace with a typed domain exception.
 */
@Service
class CreateReservationUseCaseImpl(
    private val reservationRepository: ReservationRepositoryPort,
    private val businessRepository: BusinessRepositoryPort
) : CreateReservationUseCase {
    override operator fun invoke(userId: UUID, businessId: UUID, reservation: Reservation): Reservation {
        require(businessRepository.existByPublicId(businessId)) { "Business not found" }

        return reservationRepository.createReservation(userId, businessId, reservation)
    }
}
