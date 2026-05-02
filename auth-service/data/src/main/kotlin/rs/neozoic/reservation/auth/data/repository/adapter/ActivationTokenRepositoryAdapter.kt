package rs.neozoic.reservation.auth.data.repository.adapter

import org.springframework.stereotype.Repository
import rs.neozoic.reservation.auth.data.model.mapper.toDomain
import rs.neozoic.reservation.auth.data.model.mapper.toEntity
import rs.neozoic.reservation.auth.data.repository.ActivationTokenRepository
import rs.neozoic.reservation.domain.model.ActivationToken
import rs.neozoic.reservation.domain.port.ActivationTokenRepositoryPort

@Repository
class ActivationTokenRepositoryAdapter(
    private val activationTokenRepository: ActivationTokenRepository
) : ActivationTokenRepositoryPort {

    override fun save(token: ActivationToken): ActivationToken =
        activationTokenRepository.save(token.toEntity()).toDomain()

    override fun findByToken(token: String): ActivationToken? =
        activationTokenRepository.findByToken(token)?.toDomain()

    override fun markAsUsed(token: String): ActivationToken? {
        val entity = activationTokenRepository.findByToken(token) ?: return null
        return activationTokenRepository.save(entity.copy(used = true)).toDomain()
    }
}
