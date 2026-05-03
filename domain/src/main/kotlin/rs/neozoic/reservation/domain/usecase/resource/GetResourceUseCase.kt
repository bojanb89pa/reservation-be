package rs.neozoic.reservation.domain.usecase.resource

import rs.neozoic.reservation.domain.model.Resource
import java.util.UUID

/**
 * Retrieves a resource by its public identifier.
 *
 * @param id public ID of the resource.
 * @return the matching [Resource], or null if no resource with [id] exists.
 */
interface GetResourceUseCase {
    operator fun invoke(id: UUID): Resource?
}
