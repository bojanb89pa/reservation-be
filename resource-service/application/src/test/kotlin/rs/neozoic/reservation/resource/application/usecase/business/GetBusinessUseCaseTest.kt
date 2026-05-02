package rs.neozoic.reservation.resource.application.usecase.business

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.port.BusinessRepositoryPort
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetBusinessUseCaseTest {

    private val businessRepository: BusinessRepositoryPort = mock()
    private val getBusinessUseCase = GetBusinessUseCaseImpl(businessRepository)

    @Test
    fun `execute delegates to repository with given id`() {
        val id = UUID.randomUUID()
        val business = Business(id = id, name = "Salon One")
        whenever(businessRepository.getBusinessByPublicId(id)).thenReturn(business)

        val result = getBusinessUseCase(id)

        assertEquals(business, result)
        verify(businessRepository).getBusinessByPublicId(id)
    }

    @Test
    fun `execute returns null when business not found`() {
        val id = UUID.randomUUID()
        whenever(businessRepository.getBusinessByPublicId(id)).thenReturn(null)

        val result = getBusinessUseCase(id)

        assertNull(result)
    }
}
