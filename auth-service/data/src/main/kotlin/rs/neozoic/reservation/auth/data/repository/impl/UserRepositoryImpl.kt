package rs.neozoic.reservation.auth.data.repository.impl

import org.springframework.stereotype.Repository
import rs.neozoic.reservation.auth.data.model.mapper.toDomain
import rs.neozoic.reservation.auth.data.model.mapper.toEntity
import rs.neozoic.reservation.auth.data.repository.jpa.UserJpaRepository
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.repository.UserRepository

@Repository
open class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
): UserRepository {
    override fun save(user: User): User {
        val saved = userJpaRepository.save(user.toEntity())
        return saved.toDomain()
    }

    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email)
    }

    override fun findByEmail(email: String): User? {
        return userJpaRepository.findByEmail(email)?.toDomain()
    }

}