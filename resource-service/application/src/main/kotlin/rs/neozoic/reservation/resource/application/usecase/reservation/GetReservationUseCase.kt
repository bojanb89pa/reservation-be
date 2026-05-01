package rs.neozoic.reservation.resource.application.usecase.reservation

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.domain.port.ReservationRepositoryPort
import rs.neozoic.reservation.domain.usecase.reservation.GetReservationUseCase as GetReservationUseCasePort
import java.util.UUID

@Service
class GetReservationUseCase(
    private val reservationRepository: ReservationRepositoryPort
) : GetReservationUseCasePort {
    override operator fun invoke(id: UUID): Reservation =
        reservationRepository.getReservation(id)
}
