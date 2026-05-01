package rs.neozoic.reservation.domain.usecase.reservation

import rs.neozoic.reservation.domain.model.Reservation
import java.util.UUID

interface CreateReservationUseCase {
    operator fun invoke(userId: UUID, businessId: UUID, reservation: Reservation): Reservation
}
