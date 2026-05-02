package rs.neozoic.reservation.auth.application.usecase.user

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import rs.neozoic.reservation.domain.model.ActivationToken
import rs.neozoic.reservation.domain.model.Role
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.port.ActivationTokenRepositoryPort
import rs.neozoic.reservation.domain.port.UserRepositoryPort
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ActivateUserUseCaseTest {
    private val activationTokenRepositoryPort: ActivationTokenRepositoryPort = mock()
    private val userRepositoryPort: UserRepositoryPort = mock()
    private val useCase = ActivateUserUseCaseImpl(activationTokenRepositoryPort, userRepositoryPort)

    private val userId = UUID.randomUUID()
    private val tokenString = "valid-token"
    private val validToken = ActivationToken(
        id = UUID.randomUUID(),
        userId = userId,
        token = tokenString,
        expiresAt = LocalDateTime.now().plusHours(1),
        used = false
    )
    private val activatedUser = User(
        id = userId,
        email = "user@example.com",
        roles = setOf(Role.ROLE_USER),
        firstName = "Jane",
        lastName = "Doe",
        enabled = true
    )

    @Test
    fun `invoke marks token as used, activates user, and returns activated user`() {
        whenever(activationTokenRepositoryPort.findByToken(tokenString)).thenReturn(validToken)
        whenever(userRepositoryPort.activateUser(userId)).thenReturn(activatedUser)

        val result = useCase(tokenString)

        verify(activationTokenRepositoryPort).markAsUsed(tokenString)
        verify(userRepositoryPort).activateUser(userId)
        assertEquals(activatedUser, result)
    }

    @Test
    fun `invoke throws IllegalArgumentException when token does not exist`() {
        whenever(activationTokenRepositoryPort.findByToken(tokenString)).thenReturn(null)

        assertFailsWith<IllegalArgumentException> { useCase(tokenString) }
    }

    @Test
    fun `invoke throws IllegalStateException when token has already been used`() {
        val usedToken = validToken.copy(used = true)
        whenever(activationTokenRepositoryPort.findByToken(tokenString)).thenReturn(usedToken)

        assertFailsWith<IllegalStateException> { useCase(tokenString) }
    }

    @Test
    fun `invoke throws IllegalStateException when token has expired`() {
        val expiredToken = validToken.copy(expiresAt = LocalDateTime.now().minusMinutes(1))
        whenever(activationTokenRepositoryPort.findByToken(tokenString)).thenReturn(expiredToken)

        assertFailsWith<IllegalStateException> { useCase(tokenString) }
    }
}
