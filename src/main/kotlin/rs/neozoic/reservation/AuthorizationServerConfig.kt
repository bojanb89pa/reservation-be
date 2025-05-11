package rs.neozoic.reservation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.util.matcher.RequestMatcher

@Configuration
class AuthorizationServerConfig {

    @Bean
    fun authorizationServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer()
        authorizationServerConfigurer.oidc(Customizer.withDefaults())
        val endpointsMatcher: RequestMatcher = authorizationServerConfigurer.endpointsMatcher

        return http
            .securityMatcher(endpointsMatcher)
            .authorizeHttpRequests { it.anyRequest().authenticated() }
            .csrf { it.ignoringRequestMatchers(endpointsMatcher) }
            .exceptionHandling { it.authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login")) }
            .with(authorizationServerConfigurer, Customizer.withDefaults())
            .build()
    }
}