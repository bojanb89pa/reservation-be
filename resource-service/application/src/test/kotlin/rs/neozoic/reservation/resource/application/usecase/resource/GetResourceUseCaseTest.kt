package rs.neozoic.reservation.resource.application.usecase.resource

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import rs.neozoic.reservation.domain.model.Resource
import rs.neozoic.reservation.domain.model.ResourceType
import rs.neozoic.reservation.domain.port.ResourceRepositoryPort
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetResourceUseCaseTest {

    private val resourceRepository: ResourceRepositoryPort = mock()
    private val useCase = GetResourceUseCaseImpl(resourceRepository)

    @Test
    fun `invoke delegates to repository and returns result`() {
        val id = UUID.randomUUID()
        val expected = Resource(id = id, businessId = UUID.randomUUID(), type = ResourceType.EMPLOYEE, name = "Alice")

        whenever(resourceRepository.getResourceByPublicId(id)).thenReturn(expected)

        val result = useCase(id)

        assertEquals(expected, result)
        verify(resourceRepository).getResourceByPublicId(id)
    }

    @Test
    fun `invoke returns null when resource not found`() {
        val id = UUID.randomUUID()
        whenever(resourceRepository.getResourceByPublicId(id)).thenReturn(null)

        assertNull(useCase(id))

        verify(resourceRepository).getResourceByPublicId(id)
    }
}
