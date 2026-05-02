package rs.neozoic.reservation.auth.data.repository.adapter

import org.springframework.data.domain.PageRequest as SpringPageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import rs.neozoic.reservation.auth.data.model.mapper.toDomain
import rs.neozoic.reservation.auth.data.model.mapper.toDomainInternal
import rs.neozoic.reservation.auth.data.model.mapper.toEntity
import rs.neozoic.reservation.auth.data.repository.UserRepository
import rs.neozoic.reservation.domain.model.PageRequest
import rs.neozoic.reservation.domain.model.PageResponse
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.port.UserRepositoryPort
import java.util.UUID

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

    override fun activateUser(userId: UUID): User? {
        val entity = userRepository.findByPublicId(userId) ?: return null
        return userRepository.save(entity.copy(activated = true, enabled = true)).toDomain()
    }

    override fun findAllUsers(pageRequest: PageRequest): PageResponse<User> {
        val sort = Sort.by(Sort.Direction.ASC, "id")
        return when (pageRequest) {
            is PageRequest.Offset -> {
                val pageable = SpringPageRequest.of(pageRequest.page, pageRequest.size, sort)
                val page = userRepository.findAll(pageable)
                PageResponse(
                    content = page.content.map { it.toDomain() },
                    size = page.size,
                    page = page.number,
                    totalElements = page.totalElements,
                    totalPages = page.totalPages
                )
            }
            is PageRequest.Cursor -> throw UnsupportedOperationException("Cursor pagination is not supported for users")
        }
    }
}