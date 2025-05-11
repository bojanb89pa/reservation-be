package rs.neozoic.reservation.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher

@Configuration
class DefaultSecurityConfig {

    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .securityMatcher(
                NegatedRequestMatcher(AntPathRequestMatcher("/api/**"))
            )
            .authorizeHttpRequests { it
                .requestMatchers("/login", "/public/**", "/error").permitAll()
                .anyRequest().authenticated()
            }
            .formLogin(Customizer.withDefaults())
            .csrf { csrf ->
                csrf.ignoringRequestMatchers(
                    AntPathRequestMatcher("/api/**") // ✅ Disable CSRF for API
                )
            }
            .build()
    }
}