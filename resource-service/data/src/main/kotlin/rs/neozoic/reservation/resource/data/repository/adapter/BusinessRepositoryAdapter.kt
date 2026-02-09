package rs.neozoic.reservation.resource.data.repository.adapter

import org.springframework.stereotype.Repository
import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.port.BusinessRepositoryPort
import rs.neozoic.reservation.resource.data.model.mapper.toDomain
import rs.neozoic.reservation.resource.data.model.mapper.toEntity
import rs.neozoic.reservation.resource.data.repository.BusinessRepository
import java.util.*

@Repository
class BusinessRepositoryAdapter(
    private val businessRepository: BusinessRepository
) : BusinessRepositoryPort {
    override fun createBusiness(business: Business): Business =
        businessRepository.save(business.toEntity()).toDomain()

    override fun getBusinessByPublicId(publicId: UUID) =
        businessRepository.findByPublicId(publicId).toDomain()

    override fun existByPublicId(publicId: UUID) =
        businessRepository.existsByPublicId(publicId)


}