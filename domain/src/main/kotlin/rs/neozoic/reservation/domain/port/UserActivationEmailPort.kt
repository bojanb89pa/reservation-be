package rs.neozoic.reservation.domain.port

import rs.neozoic.reservation.domain.model.User

/** Outbound port for sending account activation emails. */
interface UserActivationEmailPort {
    fun sendActivationEmail(user: User, activationToken: String)
}
