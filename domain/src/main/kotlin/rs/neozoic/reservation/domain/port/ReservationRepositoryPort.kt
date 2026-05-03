package rs.neozoic.reservation.domain.port

import rs.neozoic.reservation.domain.model.Reservation
import java.util.UUID

/** Outbound port for reservation persistence operations. */
interface ReservationRepositoryPort {
    fun createReservation(userId: UUID, resourceId: UUID, reservation: Reservation): Reservation
    fun getReservation(id: UUID): Reservation
}
