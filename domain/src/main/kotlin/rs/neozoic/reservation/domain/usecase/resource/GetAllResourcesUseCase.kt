package rs.neozoic.reservation.domain.usecase.resource

import rs.neozoic.reservation.domain.model.PageRequest
import rs.neozoic.reservation.domain.model.PageResponse
import rs.neozoic.reservation.domain.model.Resource
import java.util.UUID

/**
 * Returns a paginated list of all resources belonging to a business.
 *
 * @param businessId public ID of the owning business.
 * @param pageRequest pagination strategy and parameters.
 * @return a [PageResponse] containing resources for the requested page.
 * @throws IllegalArgumentException if no business with [businessId] exists.
 */
interface GetAllResourcesUseCase {
    operator fun invoke(businessId: UUID, pageRequest: PageRequest): PageResponse<Resource>
}
