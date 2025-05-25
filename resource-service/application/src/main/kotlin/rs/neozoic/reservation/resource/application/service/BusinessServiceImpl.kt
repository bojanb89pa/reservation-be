package rs.neozoic.reservation.resource.application.service

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.repository.BusinessRepository
import rs.neozoic.reservation.domain.service.BusinessService
import java.util.*

@Service
class BusinessServiceImpl(
    private val businessRepository: BusinessRepository
) : BusinessService{
    override fun createBusiness(business: Business): Business? =
        businessRepository.createBusiness(business)

    override fun getBusiness(id: UUID): Business? =
        businessRepository.getBusinessByPublicId(id)

}