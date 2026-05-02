package rs.neozoic.reservation.domain.port

import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.model.PageRequest
import rs.neozoic.reservation.domain.model.PageResponse
import java.util.*

/** Outbound port for business persistence operations. */
interface BusinessRepositoryPort {
    fun createBusiness(business: Business): Business?
    fun getBusinessByPublicId(publicId: UUID): Business?
    fun existByPublicId(publicId: UUID): Boolean

    /** Returns a paginated slice of all businesses ordered consistently for the given [pageRequest] strategy. */
    fun findAllBusinesses(pageRequest: PageRequest): PageResponse<Business>
}
