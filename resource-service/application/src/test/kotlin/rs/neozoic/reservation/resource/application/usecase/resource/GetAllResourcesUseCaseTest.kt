package rs.neozoic.reservation.resource.application.usecase.resource

import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import rs.neozoic.reservation.domain.model.PageRequest
import rs.neozoic.reservation.domain.model.PageResponse
import rs.neozoic.reservation.domain.model.Resource
import rs.neozoic.reservation.domain.model.ResourceType
import rs.neozoic.reservation.domain.port.BusinessRepositoryPort
import rs.neozoic.reservation.domain.port.ResourceRepositoryPort
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetAllResourcesUseCaseTest {

    private val resourceRepository: ResourceRepositoryPort = mock()
    private val businessRepository: BusinessRepositoryPort = mock()
    private val useCase = GetAllResourcesUseCaseImpl(resourceRepository, businessRepository)

    private fun resource(name: String, businessId: UUID) =
        Resource(id = UUID.randomUUID(), businessId = businessId, type = ResourceType.ROOM, name = name)

    @Test
    fun `invoke returns paged resources when business exists`() {
        val businessId = UUID.randomUUID()
        val pageRequest = PageRequest.Offset(page = 0, size = 10)
        val resources = listOf(resource("Room 1", businessId), resource("Room 2", businessId))
        val pageResponse = PageResponse(content = resources, size = 10, page = 0, totalElements = 2L, totalPages = 1)

        whenever(businessRepository.existByPublicId(businessId)).thenReturn(true)
        whenever(resourceRepository.findAllByBusinessId(businessId, pageRequest)).thenReturn(pageResponse)

        val result = useCase(businessId, pageRequest)

        assertEquals(pageResponse, result)
        verify(businessRepository).existByPublicId(businessId)
        verify(resourceRepository).findAllByBusinessId(businessId, pageRequest)
    }

    @Test
    fun `invoke returns empty page when business has no resources`() {
        val businessId = UUID.randomUUID()
        val pageRequest = PageRequest.Offset(page = 0, size = 10)
        val pageResponse = PageResponse<Resource>(content = emptyList(), size = 10, page = 0, totalElements = 0L, totalPages = 0)

        whenever(businessRepository.existByPublicId(businessId)).thenReturn(true)
        whenever(resourceRepository.findAllByBusinessId(businessId, pageRequest)).thenReturn(pageResponse)

        val result = useCase(businessId, pageRequest)

        assertEquals(pageResponse, result)
    }

    @Test
    fun `invoke throws IllegalArgumentException when business not found`() {
        val businessId = UUID.randomUUID()
        val pageRequest = PageRequest.Offset(page = 0, size = 10)

        whenever(businessRepository.existByPublicId(businessId)).thenReturn(false)

        assertFailsWith<IllegalArgumentException> {
            useCase(businessId, pageRequest)
        }

        verify(resourceRepository, never()).findAllByBusinessId(any(), any())
    }
}
