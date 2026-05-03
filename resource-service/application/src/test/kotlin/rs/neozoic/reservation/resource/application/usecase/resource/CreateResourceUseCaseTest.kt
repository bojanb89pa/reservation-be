package rs.neozoic.reservation.resource.application.usecase.resource

import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import rs.neozoic.reservation.domain.model.Resource
import rs.neozoic.reservation.domain.model.ResourceType
import rs.neozoic.reservation.domain.port.BusinessRepositoryPort
import rs.neozoic.reservation.domain.port.ResourceRepositoryPort
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateResourceUseCaseTest {

    private val resourceRepository: ResourceRepositoryPort = mock()
    private val businessRepository: BusinessRepositoryPort = mock()
    private val useCase = CreateResourceUseCaseImpl(resourceRepository, businessRepository)

    private fun resource(
        businessId: UUID = UUID.randomUUID(),
        name: String = "Room 1",
    ) = Resource(id = null, businessId = businessId, type = ResourceType.ROOM, name = name)

    @Test
    fun `invoke creates resource when business exists`() {
        val businessId = UUID.randomUUID()
        val input = resource(businessId = businessId)
        val saved = input.copy(id = UUID.randomUUID())

        whenever(businessRepository.existByPublicId(businessId)).thenReturn(true)
        whenever(resourceRepository.createResource(input)).thenReturn(saved)

        val result = useCase(businessId, input)

        assertEquals(saved, result)
        verify(businessRepository).existByPublicId(businessId)
        verify(resourceRepository).createResource(input)
    }

    @Test
    fun `invoke throws IllegalArgumentException when business not found`() {
        val businessId = UUID.randomUUID()
        val input = resource(businessId = businessId)

        whenever(businessRepository.existByPublicId(businessId)).thenReturn(false)

        assertFailsWith<IllegalArgumentException> {
            useCase(businessId, input)
        }

        verify(resourceRepository, never()).createResource(any())
    }
}
