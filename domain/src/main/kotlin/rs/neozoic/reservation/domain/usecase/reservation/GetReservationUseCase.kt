package rs.neozoic.reservation.domain.usecase.reservation

import rs.neozoic.reservation.domain.model.Reservation
import java.util.UUID

/**
 * Retrieves a reservation by its public identifier.
 *
 * @return the matching [Reservation].
 * @throws exception from the repository if no reservation with [id] exists.
 */
interface GetReservationUseCase {
    operator fun invoke(id: UUID): Reservation
}
