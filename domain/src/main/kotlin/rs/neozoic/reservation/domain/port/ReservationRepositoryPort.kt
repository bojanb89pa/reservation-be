package rs.neozoic.reservation.domain.port

import rs.neozoic.reservation.domain.model.Reservation
import java.util.*

interface ReservationRepositoryPort {
    fun createReservation(userId: UUID, businessId: UUID, reservation: Reservation): Reservation
    fun getReservation(id: UUID): Reservation
}