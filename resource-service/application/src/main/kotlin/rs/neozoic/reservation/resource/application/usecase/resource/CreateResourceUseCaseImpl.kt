package rs.neozoic.reservation.resource.application.usecase.resource

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.Resource
import rs.neozoic.reservation.domain.port.BusinessRepositoryPort
import rs.neozoic.reservation.domain.port.ResourceRepositoryPort
import rs.neozoic.reservation.domain.usecase.resource.CreateResourceUseCase
import java.util.UUID

/**
 * Validates business existence before delegating to the resource repository.
 * Throws [IllegalArgumentException] if the business is not found.
 */
@Service
class CreateResourceUseCaseImpl(
    private val resourceRepository: ResourceRepositoryPort,
    private val businessRepository: BusinessRepositoryPort
) : CreateResourceUseCase {
    override operator fun invoke(businessId: UUID, resource: Resource): Resource {
        require(businessRepository.existByPublicId(businessId)) { "Business not found" }

        return resourceRepository.createResource(resource)
    }
}
