package rs.neozoic.reservation.domain.port

import rs.neozoic.reservation.domain.model.User

/** Outbound port for user persistence operations. */
interface UserRepositoryPort {
    fun save(user: User): User
    fun existsByEmail(email: String): Boolean

    /** Returns the user with public fields populated. Used by application use cases. */
    fun findByEmail(email: String): User?

    /** Returns the user including credential fields (e.g. hashed password). Used by Spring Security internals only. */
    fun findByEmailInternal(email: String): User?
}
