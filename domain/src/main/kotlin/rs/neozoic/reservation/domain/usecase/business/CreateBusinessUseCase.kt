package rs.neozoic.reservation.domain.usecase.business

import rs.neozoic.reservation.domain.model.Business

/**
 * Creates a new business in the system.
 *
 * @return the persisted [Business] with its assigned public ID, or null if persistence failed.
 */
interface CreateBusinessUseCase {
    operator fun invoke(business: Business): Business?
}
