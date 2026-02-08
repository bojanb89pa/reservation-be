package rs.neozoic.reservation.auth.application.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import rs.neozoic.reservation.auth.application.UserDetailsImpl
import rs.neozoic.reservation.domain.service.data.UserDataService

@Service
class UserDetailsServiceImpl(
    private val userDataService: UserDataService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userDataService.findByEmailInternal(username)
            ?: throw UsernameNotFoundException("User not found with email: $username")
        return UserDetailsImpl(user)
    }
}