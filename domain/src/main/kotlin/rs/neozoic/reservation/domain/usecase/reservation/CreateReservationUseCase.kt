package rs.neozoic.reservation.domain.usecase.reservation

import rs.neozoic.reservation.domain.model.Reservation
import java.util.UUID

/**
 * Books a reservation for an authenticated user at a specific business.
 *
 * @param userId public ID of the user making the reservation; sourced from the JWT principal.
 * @param businessId public ID of the target business.
 * @param reservation the reservation details including start and end time.
 * @return the persisted [Reservation] with its assigned public ID.
 * @throws IllegalArgumentException if no business with [businessId] exists.
 */
interface CreateReservationUseCase {
    operator fun invoke(userId: UUID, businessId: UUID, reservation: Reservation): Reservation
}
