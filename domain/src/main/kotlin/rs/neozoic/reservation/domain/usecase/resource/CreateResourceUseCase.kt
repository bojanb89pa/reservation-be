package rs.neozoic.reservation.domain.usecase.resource

import rs.neozoic.reservation.domain.model.Resource
import java.util.UUID

/**
 * Creates a new resource for an existing business.
 *
 * @param businessId public ID of the business that owns the resource.
 * @param resource the resource to create.
 * @return the persisted [Resource] with its assigned public ID.
 * @throws IllegalArgumentException if no business with [businessId] exists.
 */
interface CreateResourceUseCase {
    operator fun invoke(businessId: UUID, resource: Resource): Resource
}
