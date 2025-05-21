package rs.neozoic.reservation.resource.data.repository.jpa

import org.springframework.stereotype.Repository
import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.repository.BusinessRepository
import rs.neozoic.reservation.resource.data.model.mapper.toDomain
import rs.neozoic.reservation.resource.data.model.mapper.toEntity
import java.util.*

@Repository
class BusinessRepositoryImpl(
    private val businessJpaRepository: BusinessJpaRepository
) : BusinessRepository {
    override fun createBusiness(business: Business): Business =
        businessJpaRepository.save(business.toEntity()).toDomain()

    override fun getBusinessById(id: UUID) =
        businessJpaRepository.findByPublicId(id).toDomain()

}