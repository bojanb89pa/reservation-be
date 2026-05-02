package rs.neozoic.reservation.resource.application.usecase.business

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.model.PageRequest
import rs.neozoic.reservation.domain.model.PageResponse
import rs.neozoic.reservation.domain.port.BusinessRepositoryPort
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class GetAllBusinessesUseCaseTest {

    private val businessRepository: BusinessRepositoryPort = mock()
    private val useCase = GetAllBusinessesUseCaseImpl(businessRepository)

    @Test
    fun `invoke delegates to repository and returns paged result`() {
        val pageRequest = PageRequest.Offset(page = 0, size = 10)
        val businesses = listOf(
            business(name = "Salon One"),
            business(name = "Salon Two")
        )
        val pageResponse = PageResponse(content = businesses, size = 10, page = 0, totalElements = 2L, totalPages = 1)
        whenever(businessRepository.findAllBusinesses(pageRequest)).thenReturn(pageResponse)

        val result = useCase(pageRequest)

        assertEquals(pageResponse, result)
        verify(businessRepository).findAllBusinesses(pageRequest)
    }

    @Test
    fun `invoke returns empty page when no businesses exist`() {
        val pageRequest = PageRequest.Offset(page = 0, size = 10)
        val pageResponse = PageResponse<Business>(content = emptyList(), size = 10, page = 0, totalElements = 0L, totalPages = 0)
        whenever(businessRepository.findAllBusinesses(pageRequest)).thenReturn(pageResponse)

        val result = useCase(pageRequest)

        assertEquals(pageResponse, result)
        verify(businessRepository).findAllBusinesses(pageRequest)
    }

    private fun business(name: String) = Business(id = UUID.randomUUID(), name = name)
}
