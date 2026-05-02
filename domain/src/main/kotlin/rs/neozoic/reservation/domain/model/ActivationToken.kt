package rs.neozoic.reservation.domain.model

import java.time.LocalDateTime
import java.util.UUID

/**
 * One-time token used to verify a user's email address during account activation.
 *
 * @property id surrogate identifier; null before first persistence
 * @property userId public identifier of the user this token belongs to
 * @property token cryptographically random string sent inside the activation link
 * @property expiresAt point in time after which the token is no longer valid
 * @property used true once the token has been successfully redeemed; prevents replay
 */
data class ActivationToken(
    val id: UUID?,
    val userId: UUID,
    val token: String,
    val expiresAt: LocalDateTime,
    val used: Boolean = false
)
