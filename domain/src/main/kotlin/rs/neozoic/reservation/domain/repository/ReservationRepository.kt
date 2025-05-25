package rs.neozoic.reservation.domain.repository

import rs.neozoic.reservation.domain.model.Reservation
import java.util.*

interface ReservationRepository {
    fun createReservation(userId: UUID, businessId: UUID, reservation: Reservation): Reservation
    fun getReservation(id: UUID): Reservation
}