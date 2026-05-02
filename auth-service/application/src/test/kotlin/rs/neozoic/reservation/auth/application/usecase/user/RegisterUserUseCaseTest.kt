package rs.neozoic.reservation.auth.application.usecase.user

import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import rs.neozoic.reservation.domain.model.ActivationToken
import rs.neozoic.reservation.domain.model.Role
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.model.UserRegistration
import rs.neozoic.reservation.domain.port.ActivationTokenRepositoryPort
import rs.neozoic.reservation.domain.port.UserActivationEmailPort
import rs.neozoic.reservation.domain.port.UserRepositoryPort
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RegisterUserUseCaseTest {
    private val passwordEncoder: PasswordEncoder = mock()
    private val userRepositoryPort: UserRepositoryPort = mock()
    private val activationTokenRepositoryPort: ActivationTokenRepositoryPort = mock()
    private val userActivationEmailPort: UserActivationEmailPort = mock()
    private val useCase = RegisterUserUseCaseImpl(
        passwordEncoder,
        userRepositoryPort,
        activationTokenRepositoryPort,
        userActivationEmailPort
    )

    @Test
    fun `invoke saves disabled user with ROLE_USER, creates token, and sends activation email`() {
        val registration = UserRegistration(
            email = "user@example.com",
            password = "plain",
            firstName = "Jane",
            lastName = "Doe"
        )
        val userId = UUID.randomUUID()
        val savedUser = User(
            id = userId,
            email = registration.email,
            roles = setOf(Role.ROLE_USER),
            firstName = registration.firstName,
            lastName = registration.lastName,
            enabled = false
        )
        val tokenString = "activation-token"
        val savedToken = ActivationToken(
            id = UUID.randomUUID(),
            userId = userId,
            token = tokenString,
            expiresAt = LocalDateTime.now().plusHours(24)
        )

        whenever(userRepositoryPort.existsByEmail(registration.email)).thenReturn(false)
        whenever(passwordEncoder.encode(registration.password)).thenReturn("hashed")
        whenever(userRepositoryPort.save(org.mockito.kotlin.any())).thenReturn(savedUser)
        whenever(activationTokenRepositoryPort.save(org.mockito.kotlin.any())).thenReturn(savedToken)

        val result = useCase(registration)

        val userCaptor = argumentCaptor<User>()
        verify(userRepositoryPort).save(userCaptor.capture())
        assertEquals(setOf(Role.ROLE_USER), userCaptor.firstValue.roles)
        assertEquals(false, userCaptor.firstValue.enabled)
        assertEquals("hashed", userCaptor.firstValue.password)

        val tokenCaptor = argumentCaptor<ActivationToken>()
        verify(activationTokenRepositoryPort).save(tokenCaptor.capture())
        assertEquals(userId, tokenCaptor.firstValue.userId)

        verify(userActivationEmailPort).sendActivationEmail(savedUser, tokenString)
        assertEquals(savedUser, result)
    }

    @Test
    fun `invoke throws IllegalStateException when email already exists`() {
        val registration = UserRegistration(
            email = "existing@example.com",
            password = "plain",
            firstName = "John",
            lastName = "Doe"
        )
        whenever(userRepositoryPort.existsByEmail(registration.email)).thenReturn(true)

        assertFailsWith<IllegalStateException> { useCase(registration) }
    }
}
