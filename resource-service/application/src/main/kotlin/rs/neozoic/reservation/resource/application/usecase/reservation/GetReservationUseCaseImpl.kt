package rs.neozoic.reservation.resource.application.usecase.reservation

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.domain.port.ReservationRepositoryPort
import rs.neozoic.reservation.domain.usecase.reservation.GetReservationUseCase
import java.util.UUID

@Service
class GetReservationUseCaseImpl(
    private val reservationRepository: ReservationRepositoryPort
) : GetReservationUseCase {
    override operator fun invoke(id: UUID): Reservation =
        reservationRepository.getReservation(id)
}
