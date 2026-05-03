package rs.neozoic.reservation.resource.data.repository.adapter

import org.springframework.stereotype.Repository
import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.domain.port.ReservationRepositoryPort
import rs.neozoic.reservation.resource.data.model.mapper.toDomain
import rs.neozoic.reservation.resource.data.model.mapper.toEntity
import rs.neozoic.reservation.resource.data.repository.ReservationRepository
import rs.neozoic.reservation.resource.data.repository.ResourceRepository
import java.util.*

@Repository
class ReservationRepositoryAdapter(
    private val reservationRepository: ReservationRepository,
    private val resourceRepository: ResourceRepository
) : ReservationRepositoryPort {
    override fun createReservation(userId: UUID, resourceId: UUID, reservation: Reservation): Reservation {
        val resource = resourceRepository.findByPublicId(resourceId)!!
        return reservationRepository.save(reservation.toEntity(userId = userId, resource = resource)).toDomain()
    }

    override fun getReservation(id: UUID): Reservation =
        reservationRepository.findByPublicId(id).toDomain()
}
