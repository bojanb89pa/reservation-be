package rs.neozoic.reservation.domain.usecase.reservation

import rs.neozoic.reservation.domain.model.Reservation
import java.util.UUID

interface GetReservationUseCase {
    operator fun invoke(id: UUID): Reservation
}
