package rs.neozoic.reservation.domain.service

import rs.neozoic.reservation.domain.model.Reservation
import java.util.*

interface ReservationService {
    fun createReservation(userId: UUID, businessId: UUID, reservation: Reservation): Reservation
    fun getReservation(id: UUID): Reservation
}