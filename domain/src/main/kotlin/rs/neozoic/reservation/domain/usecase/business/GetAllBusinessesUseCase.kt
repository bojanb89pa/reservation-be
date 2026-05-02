package rs.neozoic.reservation.domain.usecase.business

import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.model.PageRequest
import rs.neozoic.reservation.domain.model.PageResponse

/**
 * Retrieves a paginated list of all registered businesses. Requires [rs.neozoic.reservation.domain.model.Role.ROLE_ADMIN];
 * access control is enforced at the API layer.
 *
 * @param pageRequest pagination strategy and parameters — either offset-based or cursor-based
 * @return a [PageResponse] containing the requested page of businesses
 */
interface GetAllBusinessesUseCase {
    operator fun invoke(pageRequest: PageRequest): PageResponse<Business>
}
