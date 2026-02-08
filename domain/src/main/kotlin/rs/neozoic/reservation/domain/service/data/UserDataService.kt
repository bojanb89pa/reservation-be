package rs.neozoic.reservation.domain.service.data

import rs.neozoic.reservation.domain.model.User

interface UserDataService {
    fun save(user: User): User
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?
    fun findByEmailInternal(email: String): User?
}