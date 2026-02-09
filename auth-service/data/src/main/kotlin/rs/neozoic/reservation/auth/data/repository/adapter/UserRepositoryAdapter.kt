package rs.neozoic.reservation.auth.data.repository.adapter

import org.springframework.stereotype.Repository
import rs.neozoic.reservation.auth.data.model.mapper.toDomain
import rs.neozoic.reservation.auth.data.model.mapper.toDomainInternal
import rs.neozoic.reservation.auth.data.model.mapper.toEntity
import rs.neozoic.reservation.auth.data.repository.UserRepository
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.port.UserRepositoryPort

@Repository
class UserRepositoryAdapter(
    private val userRepository: UserRepository
): UserRepositoryPort {
    override fun save(user: User): User {
        val saved = userRepository.save(user.toEntity())
        return saved.toDomain()
    }

    override fun existsByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)?.toDomain()
    }

    override fun findByEmailInternal(email: String): User? {
        return userRepository.findByEmail(email)?.toDomainInternal()
    }

}