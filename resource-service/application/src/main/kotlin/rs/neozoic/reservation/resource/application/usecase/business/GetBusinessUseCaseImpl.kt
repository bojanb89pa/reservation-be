package rs.neozoic.reservation.resource.application.usecase.business

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.port.BusinessRepositoryPort
import rs.neozoic.reservation.domain.usecase.business.GetBusinessUseCase
import java.util.UUID

@Service
class GetBusinessUseCaseImpl(
    private val businessRepository: BusinessRepositoryPort
) : GetBusinessUseCase {
    override operator fun invoke(id: UUID): Business? =
        businessRepository.getBusinessByPublicId(id)
}
