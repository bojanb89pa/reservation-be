package rs.neozoic.reservation.auth

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*

@Configuration
class JwtKeyConfig {

    @Bean
    fun jwkSource(): JWKSource<SecurityContext> {
        val keypair = KeyPairGenerator.getInstance("RSA").apply { initialize(2048) }.genKeyPair()
        val rsaKey = RSAKey.Builder(keypair.public as RSAPublicKey)
            .privateKey(keypair.private as RSAPrivateKey)
            .keyID(UUID.randomUUID().toString())
            .build()
        val jwkSet = JWKSet(rsaKey)
        return ImmutableJWKSet(jwkSet)
    }
}