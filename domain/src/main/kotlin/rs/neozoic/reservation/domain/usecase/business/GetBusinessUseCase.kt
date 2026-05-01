package rs.neozoic.reservation.domain.usecase.business

import rs.neozoic.reservation.domain.model.Business
import java.util.UUID

interface GetBusinessUseCase {
    operator fun invoke(id: UUID): Business?
}
