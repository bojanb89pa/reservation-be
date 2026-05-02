package rs.neozoic.reservation.domain.port

import rs.neozoic.reservation.domain.model.ActivationToken

interface ActivationTokenRepositoryPort {
    fun save(token: ActivationToken): ActivationToken
    fun findByToken(token: String): ActivationToken?

    /** Marks the token as used and returns the updated record, or null if the token does not exist. */
    fun markAsUsed(token: String): ActivationToken?
}
