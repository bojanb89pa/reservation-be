package rs.neozoic.reservation.domain.port

import rs.neozoic.reservation.domain.model.User

interface UserRepositoryPort {
    fun save(user: User): User
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?
    fun findByEmailInternal(email: String): User?
}