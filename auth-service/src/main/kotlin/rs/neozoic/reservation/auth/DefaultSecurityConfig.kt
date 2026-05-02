package rs.neozoic.reservation.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.NegatedRequestMatcher
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class DefaultSecurityConfig {

    @Bean
    fun swaggerWebSecurityCustomizer(): WebSecurityCustomizer =
        WebSecurityCustomizer { web -> web.ignoring().requestMatchers("/swagger-ui/**", "/v3/api-docs/**") }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()
        config.allowedOrigins = listOf("http://localhost:8080", "http://localhost:8081")
        config.allowedMethods = listOf("GET", "POST")
        config.allowedHeaders = listOf("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/oauth2/**", config)
        return source
    }

    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .securityMatcher(
                NegatedRequestMatcher(PathPatternRequestMatcher.withDefaults().matcher("/api/**"))
            )
            .authorizeHttpRequests { it
                .requestMatchers("/login", "/public/**", "/error").permitAll()
                .anyRequest().authenticated()
            }
            .formLogin { form -> form.loginPage("/login") }
            .cors(Customizer.withDefaults())
            .csrf { csrf ->
                csrf.ignoringRequestMatchers(
                    PathPatternRequestMatcher.withDefaults().matcher("/api/**")
                )
            }
            .build()
    }
}