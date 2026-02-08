package rs.neozoic.reservation.domain.service.data

import rs.neozoic.reservation.domain.model.Reservation
import java.util.*

interface ReservationDataService {
    fun createReservation(userId: UUID, businessId: UUID, reservation: Reservation): Reservation
    fun getReservation(id: UUID): Reservation
}