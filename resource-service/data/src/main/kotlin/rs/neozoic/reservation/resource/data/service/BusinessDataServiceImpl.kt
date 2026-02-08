package rs.neozoic.reservation.resource.data.service

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.service.data.BusinessDataService
import rs.neozoic.reservation.resource.data.model.mapper.toDomain
import rs.neozoic.reservation.resource.data.model.mapper.toEntity
import rs.neozoic.reservation.resource.data.repository.BusinessRepository
import java.util.*

@Service
class BusinessDataServiceImpl(
    private val businessRepository: BusinessRepository
) : BusinessDataService {
    override fun createBusiness(business: Business): Business =
        businessRepository.save(business.toEntity()).toDomain()

    override fun getBusinessByPublicId(publicId: UUID) =
        businessRepository.findByPublicId(publicId).toDomain()

    override fun existByPublicId(publicId: UUID) =
        businessRepository.existsByPublicId(publicId)


}