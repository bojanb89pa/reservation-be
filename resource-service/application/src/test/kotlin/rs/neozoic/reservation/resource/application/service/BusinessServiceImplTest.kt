package rs.neozoic.reservation.resource.application.service

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.port.BusinessRepositoryPort
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class BusinessServiceImplTest {

    private val businessRepository: BusinessRepositoryPort = mock()
    private val businessService = BusinessServiceImpl(businessRepository)

    @Test
    fun `createBusiness delegates to repository and returns result`() {
        val business = Business(id = UUID.randomUUID(), name = "Salon One")
        whenever(businessRepository.createBusiness(business)).thenReturn(business)

        val result = businessService.createBusiness(business)

        assertEquals(business, result)
        verify(businessRepository).createBusiness(business)
    }

    @Test
    fun `createBusiness returns null when repository returns null`() {
        val business = Business(id = null, name = "Salon One")
        whenever(businessRepository.createBusiness(business)).thenReturn(null)

        val result = businessService.createBusiness(business)

        assertNull(result)
    }

    @Test
    fun `getBusiness delegates to repository with given id`() {
        val id = UUID.randomUUID()
        val business = Business(id = id, name = "Salon One")
        whenever(businessRepository.getBusinessByPublicId(id)).thenReturn(business)

        val result = businessService.getBusiness(id)

        assertEquals(business, result)
        verify(businessRepository).getBusinessByPublicId(id)
    }

    @Test
    fun `getBusiness returns null when business not found`() {
        val id = UUID.randomUUID()
        whenever(businessRepository.getBusinessByPublicId(id)).thenReturn(null)

        val result = businessService.getBusiness(id)

        assertNull(result)
    }
}
