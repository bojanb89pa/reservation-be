package rs.neozoic.reservation.domain.port

import rs.neozoic.reservation.domain.model.PageRequest
import rs.neozoic.reservation.domain.model.PageResponse
import rs.neozoic.reservation.domain.model.Resource
import java.util.UUID

/** Outbound port for resource persistence operations. */
interface ResourceRepositoryPort {
    fun createResource(resource: Resource): Resource
    fun getResourceByPublicId(publicId: UUID): Resource?
    fun existsByPublicId(publicId: UUID): Boolean

    /** Returns a paginated slice of all resources belonging to [businessId]. */
    fun findAllByBusinessId(businessId: UUID, pageRequest: PageRequest): PageResponse<Resource>
}
