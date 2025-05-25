package rs.neozoic.reservation.auth.application

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import rs.neozoic.reservation.domain.model.User

class UserDetailsImpl(
    private val user: User
) : UserDetails {
    override fun getAuthorities() = user.roles.map {
        SimpleGrantedAuthority(it.name)
    }.toList()

    override fun getPassword() = user.password
    override fun getUsername() = user.email
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = user.enabled ?: false

    val subject = user.id
    val email = user.email
    val roles = user.roles
}