package rs.neozoic.reservation.resource.application.usecase.reservation

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.domain.port.ReservationRepositoryPort
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class GetReservationUseCaseTest {

    private val reservationRepository: ReservationRepositoryPort = mock()
    private val getReservationUseCase = GetReservationUseCaseImpl(reservationRepository)

    @Test
    fun `execute delegates to repository and returns result`() {
        val id = UUID.randomUUID()
        val expected = Reservation(
            id = id,
            userId = UUID.randomUUID(),
            resourceId = UUID.randomUUID(),
            startTime = LocalDateTime.of(2026, 5, 10, 10, 0),
            endTime = LocalDateTime.of(2026, 5, 10, 11, 0),
        )
        whenever(reservationRepository.getReservation(id)).thenReturn(expected)

        val result = getReservationUseCase(id)

        assertEquals(expected, result)
        verify(reservationRepository).getReservation(id)
    }
}
