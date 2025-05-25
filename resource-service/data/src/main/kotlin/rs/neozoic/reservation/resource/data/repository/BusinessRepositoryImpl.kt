package rs.neozoic.reservation.resource.data.repository

import org.springframework.stereotype.Repository
import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.repository.BusinessRepository
import rs.neozoic.reservation.resource.data.model.mapper.toDomain
import rs.neozoic.reservation.resource.data.model.mapper.toEntity
import rs.neozoic.reservation.resource.data.repository.jpa.BusinessJpaRepository
import java.util.*

@Repository
class BusinessRepositoryImpl(
    private val businessJpaRepository: BusinessJpaRepository
) : BusinessRepository {
    override fun createBusiness(business: Business): Business =
        businessJpaRepository.save(business.toEntity()).toDomain()

    override fun getBusinessByPublicId(publicId: UUID) =
        businessJpaRepository.findByPublicId(publicId).toDomain()

    override fun existByPublicId(publicId: UUID) =
        businessJpaRepository.existsByPublicId(publicId)


}