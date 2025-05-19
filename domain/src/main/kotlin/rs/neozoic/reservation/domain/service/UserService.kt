package rs.neozoic.reservation.domain.service

import rs.neozoic.reservation.domain.model.User

interface UserService {
    fun createUser(user: User): User
    fun getUserByEmail(email: String): User?
}