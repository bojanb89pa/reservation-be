package rs.neozoic.reservation.domain.repository

import rs.neozoic.reservation.domain.model.Business
import java.util.*

interface BusinessRepository {
    fun createBusiness(business: Business): Business?
    fun getBusinessById(id: UUID): Business?
}