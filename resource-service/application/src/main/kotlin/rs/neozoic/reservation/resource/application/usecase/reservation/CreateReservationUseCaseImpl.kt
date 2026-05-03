package rs.neozoic.reservation.resource.application.usecase.reservation

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.domain.port.ReservationRepositoryPort
import rs.neozoic.reservation.domain.port.ResourceRepositoryPort
import rs.neozoic.reservation.domain.usecase.reservation.CreateReservationUseCase
import java.util.UUID

/**
 * Validates resource existence before delegating to the reservation repository.
 * Throws [IllegalArgumentException] if the resource is not found.
 */
@Service
class CreateReservationUseCaseImpl(
    private val reservationRepository: ReservationRepositoryPort,
    private val resourceRepository: ResourceRepositoryPort
) : CreateReservationUseCase {
    override operator fun invoke(userId: UUID, resourceId: UUID, reservation: Reservation): Reservation {
        require(resourceRepository.existsByPublicId(resourceId)) { "Resource not found" }

        return reservationRepository.createReservation(userId, resourceId, reservation)
    }
}
