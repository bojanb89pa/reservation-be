package rs.neozoic.reservation.domain.usecase.business

import rs.neozoic.reservation.domain.model.Business

interface CreateBusinessUseCase {
    operator fun invoke(business: Business): Business?
}
