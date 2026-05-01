package rs.neozoic.reservation.resource.application.usecase.business

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.port.BusinessRepositoryPort
import rs.neozoic.reservation.domain.usecase.business.GetBusinessUseCase as GetBusinessUseCasePort
import java.util.UUID

@Service
class GetBusinessUseCase(
    private val businessRepository: BusinessRepositoryPort
) : GetBusinessUseCasePort {
    override operator fun invoke(id: UUID): Business? =
        businessRepository.getBusinessByPublicId(id)
}
