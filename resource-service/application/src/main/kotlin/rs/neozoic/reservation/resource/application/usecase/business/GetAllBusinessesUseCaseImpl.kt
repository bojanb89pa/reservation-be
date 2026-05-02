package rs.neozoic.reservation.resource.application.usecase.business

import org.springframework.stereotype.Service
import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.model.PageRequest
import rs.neozoic.reservation.domain.model.PageResponse
import rs.neozoic.reservation.domain.port.BusinessRepositoryPort
import rs.neozoic.reservation.domain.usecase.business.GetAllBusinessesUseCase

@Service
class GetAllBusinessesUseCaseImpl(
    private val businessRepository: BusinessRepositoryPort
) : GetAllBusinessesUseCase {
    override operator fun invoke(pageRequest: PageRequest): PageResponse<Business> =
        businessRepository.findAllBusinesses(pageRequest)
}
