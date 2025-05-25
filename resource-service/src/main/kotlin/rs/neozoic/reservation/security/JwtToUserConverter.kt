package rs.neozoic.reservation.security

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.oauth2.jwt.Jwt
import rs.neozoic.reservation.domain.constants.SecurityConstants
import rs.neozoic.reservation.domain.model.AuthenticatedUser
import rs.neozoic.reservation.domain.model.Role
import java.util.*

class JwtToUserConverter : Converter<Jwt, AbstractAuthenticationToken> {
    override fun convert(jwt: Jwt): AbstractAuthenticationToken {
        val userId = UUID.fromString(jwt.claims[SecurityConstants.Jwt.USER_ID] as String)
        val email = jwt.claims[SecurityConstants.Jwt.EMAIL] as String
        val roles: Set<Role> = (jwt.claims[SecurityConstants.Jwt.ROLES] as? Set<*>)
            ?.mapNotNull { (it as? Role) }
            ?.toSet() ?: emptySet()

        val principal = AuthenticatedUser(userId, email, roles)
        return UsernamePasswordAuthenticationToken(principal, "N/A", listOf())
    }
}