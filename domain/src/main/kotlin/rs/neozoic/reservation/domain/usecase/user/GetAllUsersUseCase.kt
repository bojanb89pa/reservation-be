package rs.neozoic.reservation.domain.usecase.user

import rs.neozoic.reservation.domain.model.PageRequest
import rs.neozoic.reservation.domain.model.PageResponse
import rs.neozoic.reservation.domain.model.User

/**
 * Retrieves a paginated list of all registered users. Requires [rs.neozoic.reservation.domain.model.Role.ROLE_ADMIN];
 * access control is enforced at the API layer.
 *
 * @param pageRequest pagination strategy and parameters — either offset-based or cursor-based
 * @return a [PageResponse] containing the requested page of users
 */
interface GetAllUsersUseCase {
    operator fun invoke(pageRequest: PageRequest): PageResponse<User>
}
