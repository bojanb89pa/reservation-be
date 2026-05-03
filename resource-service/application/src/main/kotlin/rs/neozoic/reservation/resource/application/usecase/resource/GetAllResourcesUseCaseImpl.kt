package rs.neozoic.reservation.resource.application.usecase.resource

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.PageRequest
import rs.neozoic.reservation.domain.model.PageResponse
import rs.neozoic.reservation.domain.model.Resource
import rs.neozoic.reservation.domain.port.BusinessRepositoryPort
import rs.neozoic.reservation.domain.port.ResourceRepositoryPort
import rs.neozoic.reservation.domain.usecase.resource.GetAllResourcesUseCase
import java.util.UUID

/**
 * Validates business existence before querying resources.
 * Throws [IllegalArgumentException] if the business is not found.
 */
@Service
class GetAllResourcesUseCaseImpl(
    private val resourceRepository: ResourceRepositoryPort,
    private val businessRepository: BusinessRepositoryPort
) : GetAllResourcesUseCase {
    override operator fun invoke(businessId: UUID, pageRequest: PageRequest): PageResponse<Resource> {
        require(businessRepository.existByPublicId(businessId)) { "Business not found" }

        return resourceRepository.findAllByBusinessId(businessId, pageRequest)
    }
}
