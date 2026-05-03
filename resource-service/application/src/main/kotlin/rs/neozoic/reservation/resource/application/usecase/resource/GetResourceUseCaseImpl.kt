package rs.neozoic.reservation.resource.application.usecase.resource

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.Resource
import rs.neozoic.reservation.domain.port.ResourceRepositoryPort
import rs.neozoic.reservation.domain.usecase.resource.GetResourceUseCase
import java.util.UUID

/** @see GetResourceUseCase */
@Service
class GetResourceUseCaseImpl(
    private val resourceRepository: ResourceRepositoryPort
) : GetResourceUseCase {
    override operator fun invoke(id: UUID): Resource? =
        resourceRepository.getResourceByPublicId(id)
}
