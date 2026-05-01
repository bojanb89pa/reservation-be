package rs.neozoic.reservation.resource.application.usecase.business

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.port.BusinessRepositoryPort
import rs.neozoic.reservation.domain.usecase.business.CreateBusinessUseCase as CreateBusinessUseCasePort

@Service
class CreateBusinessUseCase(
    private val businessRepository: BusinessRepositoryPort
) : CreateBusinessUseCasePort {
    override operator fun invoke(business: Business): Business? =
        businessRepository.createBusiness(business)
}
