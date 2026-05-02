package rs.neozoic.reservation.domain.port

import rs.neozoic.reservation.domain.model.PageRequest
import rs.neozoic.reservation.domain.model.PageResponse
import rs.neozoic.reservation.domain.model.User
import java.util.UUID

/** Outbound port for user persistence operations. */
interface UserRepositoryPort {
    fun save(user: User): User
    fun existsByEmail(email: String): Boolean

    /** Returns the user with public fields populated. Used by application use cases. */
    fun findByEmail(email: String): User?

    /** Returns the user including credential fields (e.g. hashed password). Used by Spring Security internals only. */
    fun findByEmailInternal(email: String): User?

    /** Sets the user's enabled flag to true and returns the updated user, or null if not found. */
    fun activateUser(userId: UUID): User?

    /** Returns a paginated slice of all users ordered consistently for the given [pageRequest] strategy. */
    fun findAllUsers(pageRequest: PageRequest): PageResponse<User>
}
