package rs.neozoic.reservation.resource.application.usecase.reservation

import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.domain.port.ReservationRepositoryPort
import rs.neozoic.reservation.domain.port.ResourceRepositoryPort
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateReservationUseCaseTest {

    private val reservationRepository: ReservationRepositoryPort = mock()
    private val resourceRepository: ResourceRepositoryPort = mock()
    private val createReservationUseCase = CreateReservationUseCaseImpl(reservationRepository, resourceRepository)

    private fun reservation(
        id: UUID? = null,
        userId: UUID = UUID.randomUUID(),
        resourceId: UUID = UUID.randomUUID(),
    ) = Reservation(
        id = id,
        userId = userId,
        resourceId = resourceId,
        startTime = LocalDateTime.of(2026, 5, 10, 10, 0),
        endTime = LocalDateTime.of(2026, 5, 10, 11, 0),
    )

    @Test
    fun `invoke saves reservation when resource exists`() {
        val userId = UUID.randomUUID()
        val resourceId = UUID.randomUUID()
        val input = reservation(userId = userId, resourceId = resourceId)
        val saved = input.copy(id = UUID.randomUUID())

        whenever(resourceRepository.existsByPublicId(resourceId)).thenReturn(true)
        whenever(reservationRepository.createReservation(userId, resourceId, input)).thenReturn(saved)

        val result = createReservationUseCase(userId, resourceId, input)

        assertEquals(saved, result)
        verify(resourceRepository).existsByPublicId(resourceId)
        verify(reservationRepository).createReservation(userId, resourceId, input)
    }

    @Test
    fun `invoke throws IllegalArgumentException when resource not found`() {
        val userId = UUID.randomUUID()
        val resourceId = UUID.randomUUID()
        val input = reservation(userId = userId, resourceId = resourceId)

        whenever(resourceRepository.existsByPublicId(resourceId)).thenReturn(false)

        assertFailsWith<IllegalArgumentException> {
            createReservationUseCase(userId, resourceId, input)
        }

        verify(reservationRepository, never()).createReservation(any(), any(), any())
    }
}
