package rs.neozoic.reservation.resource.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.OAuthFlow
import io.swagger.v3.oas.models.security.OAuthFlows
import io.swagger.v3.oas.models.security.Scopes
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun openApi(): OpenAPI = OpenAPI()
        .info(Info().title("Resource Service API").version("1.0"))
        .addSecurityItem(SecurityRequirement().addList("oauth2", listOf("openid", "profile")))
        .components(
            Components().addSecuritySchemes(
                "oauth2",
                SecurityScheme()
                    .type(SecurityScheme.Type.OAUTH2)
                    .flows(OAuthFlows().authorizationCode(OAuthFlow()
                        .authorizationUrl("http://localhost:8081/oauth2/authorize")
                        .tokenUrl("http://localhost:8081/oauth2/token")
                        .scopes(Scopes()
                            .addString("openid", "OpenID Connect")
                            .addString("profile", "Profile")
                            .addString("read", "Read access")
                            .addString("write", "Write access")
                        )
                    ))
            )
        )
}
