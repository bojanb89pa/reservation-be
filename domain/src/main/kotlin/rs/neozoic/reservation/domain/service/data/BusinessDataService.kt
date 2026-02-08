package rs.neozoic.reservation.domain.service.data

import rs.neozoic.reservation.domain.model.Business
import java.util.*

interface BusinessDataService {
    fun createBusiness(business: Business): Business?
    fun getBusinessByPublicId(publicId: UUID): Business?
    fun existByPublicId(publicId: UUID): Boolean
}