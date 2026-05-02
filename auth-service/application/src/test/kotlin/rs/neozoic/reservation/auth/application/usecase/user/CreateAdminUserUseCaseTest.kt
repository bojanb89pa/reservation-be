package rs.neozoic.reservation.auth.application.usecase.user

import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.security.crypto.password.PasswordEncoder
import rs.neozoic.reservation.domain.model.Role
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.model.UserRegistration
import rs.neozoic.reservation.domain.port.UserRepositoryPort
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateAdminUserUseCaseTest {
    private val passwordEncoder: PasswordEncoder = mock()
    private val userRepositoryPort: UserRepositoryPort = mock()
    private val useCase = CreateAdminUserUseCaseImpl(passwordEncoder, userRepositoryPort)

    @Test
    fun `invoke saves enabled user with ROLE_USER and ROLE_ADMIN and returns result`() {
        val registration = UserRegistration(
            email = "admin@example.com",
            password = "plain",
            firstName = "Admin",
            lastName = "User"
        )
        val savedUser = User(
            id = UUID.randomUUID(),
            email = registration.email,
            roles = setOf(Role.ROLE_USER, Role.ROLE_ADMIN),
            firstName = registration.firstName,
            lastName = registration.lastName,
            enabled = true
        )

        whenever(userRepositoryPort.existsByEmail(registration.email)).thenReturn(false)
        whenever(passwordEncoder.encode(registration.password)).thenReturn("hashed")
        whenever(userRepositoryPort.save(org.mockito.kotlin.any())).thenReturn(savedUser)

        val result = useCase(registration)

        val captor = argumentCaptor<User>()
        verify(userRepositoryPort).save(captor.capture())
        assertEquals(setOf(Role.ROLE_USER, Role.ROLE_ADMIN), captor.firstValue.roles)
        assertEquals(true, captor.firstValue.enabled)
        assertEquals("hashed", captor.firstValue.password)
        assertEquals(savedUser, result)
    }

    @Test
    fun `invoke throws IllegalStateException when email already exists`() {
        val registration = UserRegistration(
            email = "existing@example.com",
            password = "plain",
            firstName = "Admin",
            lastName = "User"
        )
        whenever(userRepositoryPort.existsByEmail(registration.email)).thenReturn(true)

        assertFailsWith<IllegalStateException> { useCase(registration) }
    }
}
