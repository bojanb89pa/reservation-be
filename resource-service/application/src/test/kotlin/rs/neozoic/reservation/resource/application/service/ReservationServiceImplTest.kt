package rs.neozoic.reservation.resource.application.service

import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.domain.port.BusinessRepositoryPort
import rs.neozoic.reservation.domain.port.ReservationRepositoryPort
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ReservationServiceImplTest {

    private val reservationRepository: ReservationRepositoryPort = mock()
    private val businessRepository: BusinessRepositoryPort = mock()
    private val reservationService = ReservationServiceImpl(reservationRepository, businessRepository)

    private fun reservation(
        id: UUID? = null,
        userId: UUID = UUID.randomUUID(),
        businessId: UUID = UUID.randomUUID(),
    ) = Reservation(
        id = id,
        userId = userId,
        businessId = businessId,
        startTime = LocalDateTime.of(2026, 5, 10, 10, 0),
        endTime = LocalDateTime.of(2026, 5, 10, 11, 0),
    )

    @Test
    fun `createReservation saves reservation when business exists`() {
        val userId = UUID.randomUUID()
        val businessId = UUID.randomUUID()
        val input = reservation(userId = userId, businessId = businessId)
        val saved = input.copy(id = UUID.randomUUID())

        whenever(businessRepository.existByPublicId(businessId)).thenReturn(true)
        whenever(reservationRepository.createReservation(userId, businessId, input)).thenReturn(saved)

        val result = reservationService.createReservation(userId, businessId, input)

        assertEquals(saved, result)
        verify(businessRepository).existByPublicId(businessId)
        verify(reservationRepository).createReservation(userId, businessId, input)
    }

    @Test
    fun `createReservation throws IllegalArgumentException when business not found`() {
        val userId = UUID.randomUUID()
        val businessId = UUID.randomUUID()
        val input = reservation(userId = userId, businessId = businessId)

        whenever(businessRepository.existByPublicId(businessId)).thenReturn(false)

        assertFailsWith<IllegalArgumentException> {
            reservationService.createReservation(userId, businessId, input)
        }

        verify(reservationRepository, never()).createReservation(any(), any(), any())
    }

    @Test
    fun `getReservation delegates to repository and returns result`() {
        val id = UUID.randomUUID()
        val expected = reservation(id = id)
        whenever(reservationRepository.getReservation(id)).thenReturn(expected)

        val result = reservationService.getReservation(id)

        assertEquals(expected, result)
        verify(reservationRepository).getReservation(id)
    }
}
