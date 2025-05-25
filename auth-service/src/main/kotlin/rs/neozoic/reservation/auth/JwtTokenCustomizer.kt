package rs.neozoic.reservation.auth

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer
import org.springframework.stereotype.Component
import rs.neozoic.reservation.auth.application.UserDetailsImpl
import rs.neozoic.reservation.domain.constants.SecurityConstants

@Component
class JwtTokenCustomizer : OAuth2TokenCustomizer<JwtEncodingContext> {

    override fun customize(context: JwtEncodingContext) {
        if (context.tokenType == OAuth2TokenType.ACCESS_TOKEN) {
            val authentication = context.getPrincipal<Authentication>()
            val principal = authentication.principal as? UserDetailsImpl
                ?: return
            val claims = context.claims

            claims.claim(SecurityConstants.Jwt.EMAIL, principal.email)
            claims.claim(SecurityConstants.Jwt.ROLES, principal.roles.map { it.name })

            claims.subject(principal.subject.toString())
        }
    }
}