package rs.neozoic.reservation.domain.usecase.reservation

import rs.neozoic.reservation.domain.model.Reservation
import java.util.UUID

/**
 * Books a reservation for an authenticated user against a specific resource.
 *
 * The requested time slot must fall within one of the resource's availability rules;
 * otherwise the operation must be rejected.
 *
 * @param userId public ID of the user making the reservation; sourced from the JWT principal.
 * @param resourceId public ID of the target resource.
 * @param reservation the reservation details including start and end time.
 * @return the persisted [Reservation] with its assigned public ID.
 * @throws IllegalArgumentException if no resource with [resourceId] exists, or if the
 *   requested slot does not fall within the resource's availability rules.
 */
interface CreateReservationUseCase {
    operator fun invoke(userId: UUID, resourceId: UUID, reservation: Reservation): Reservation
}
