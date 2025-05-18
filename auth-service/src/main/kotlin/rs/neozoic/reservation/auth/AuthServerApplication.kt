package rs.neozoic.reservation.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import rs.neozoic.reservation.auth.application.impl.UserServiceImpl

@SpringBootApplication
@EnableJpaRepositories
class AuthServerApplication

fun main(args: Array<String>) {
    runApplication<AuthServerApplication>(*args)
}