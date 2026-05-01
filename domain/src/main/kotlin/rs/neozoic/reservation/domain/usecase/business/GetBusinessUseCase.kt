package rs.neozoic.reservation.domain.usecase.business

import rs.neozoic.reservation.domain.model.Business
import java.util.UUID

/**
 * Retrieves a business by its public identifier.
 *
 * @return the matching [Business], or null if no business with the given [id] exists.
 */
interface GetBusinessUseCase {
    operator fun invoke(id: UUID): Business?
}
