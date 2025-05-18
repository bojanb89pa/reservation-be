package rs.neozoic.reservation.domain.repository

import rs.neozoic.reservation.domain.model.User

interface UserRepository {
    fun save(user: User): User;
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?
}