package rs.neozoic.reservation.auth.application.usecase.user

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import rs.neozoic.reservation.domain.model.PageRequest
import rs.neozoic.reservation.domain.model.PageResponse
import rs.neozoic.reservation.domain.model.Role
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.port.UserRepositoryPort
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class GetAllUsersUseCaseTest {
    private val userRepositoryPort: UserRepositoryPort = mock()
    private val useCase = GetAllUsersUseCaseImpl(userRepositoryPort)

    @Test
    fun `invoke delegates to repository and returns paged result`() {
        val pageRequest = PageRequest.Offset(page = 0, size = 10)
        val users = listOf(
            User(id = UUID.randomUUID(), email = "a@example.com", roles = setOf(Role.ROLE_USER), firstName = "Alice", lastName = "Smith"),
            User(id = UUID.randomUUID(), email = "b@example.com", roles = setOf(Role.ROLE_USER), firstName = "Bob", lastName = "Jones")
        )
        val pageResponse = PageResponse(content = users, size = 10, page = 0, totalElements = 2L, totalPages = 1)
        whenever(userRepositoryPort.findAllUsers(pageRequest)).thenReturn(pageResponse)

        val result = useCase(pageRequest)

        assertEquals(pageResponse, result)
        verify(userRepositoryPort).findAllUsers(pageRequest)
    }

    @Test
    fun `invoke returns empty page when no users exist`() {
        val pageRequest = PageRequest.Offset(page = 0, size = 10)
        val pageResponse = PageResponse<User>(content = emptyList(), size = 10, page = 0, totalElements = 0L, totalPages = 0)
        whenever(userRepositoryPort.findAllUsers(pageRequest)).thenReturn(pageResponse)

        val result = useCase(pageRequest)

        assertEquals(pageResponse, result)
        verify(userRepositoryPort).findAllUsers(pageRequest)
    }
}
