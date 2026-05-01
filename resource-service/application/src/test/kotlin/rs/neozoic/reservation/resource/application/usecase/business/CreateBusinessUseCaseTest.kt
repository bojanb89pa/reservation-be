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

class CreateBusinessUseCaseTest {

    private val businessRepository: BusinessRepositoryPort = mock()
    private val createBusinessUseCase = CreateBusinessUseCase(businessRepository)

    @Test
    fun `execute delegates to repository and returns result`() {
        val business = Business(id = UUID.randomUUID(), name = "Salon One")
        whenever(businessRepository.createBusiness(business)).thenReturn(business)

        val result = createBusinessUseCase(business)

        assertEquals(business, result)
        verify(businessRepository).createBusiness(business)
    }

    @Test
    fun `execute returns null when repository returns null`() {
        val business = Business(id = null, name = "Salon One")
        whenever(businessRepository.createBusiness(business)).thenReturn(null)

        val result = createBusinessUseCase(business)

        assertNull(result)
    }
}
