package rs.neozoic.reservation.domain.service

import rs.neozoic.reservation.domain.model.Business
import java.util.*

interface BusinessService {
    fun createBusiness(business: Business): Business?
    fun getBusiness(id: UUID): Business?
}