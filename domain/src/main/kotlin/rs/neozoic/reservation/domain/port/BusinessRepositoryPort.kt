package rs.neozoic.reservation.domain.port

import rs.neozoic.reservation.domain.model.Business
import java.util.*

/** Outbound port for business persistence operations. */
interface BusinessRepositoryPort {
    fun createBusiness(business: Business): Business?
    fun getBusinessByPublicId(publicId: UUID): Business?
    fun existByPublicId(publicId: UUID): Boolean
}
