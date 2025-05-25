package rs.neozoic.reservation.resource.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import rs.neozoic.reservation.domain.model.AuthenticatedUser
import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.domain.service.ReservationService
import java.util.*

@RestController
@RequestMapping("/api/businesses/{businessId}/reservations")
class ReservationController(
    private val reservationService: ReservationService
) {
    @PostMapping
    fun createReservation(
        @PathVariable businessId: UUID,
        @AuthenticationPrincipal authUser: AuthenticatedUser,
        @RequestBody reservation: Reservation
    ): ResponseEntity<Reservation> =
        ResponseEntity.ok(reservationService.createReservation(authUser.id, businessId, reservation))

    @GetMapping("/{id}")
    fun getReservation(@PathVariable id: UUID): ResponseEntity<Reservation> =
        ResponseEntity.ok(reservationService.getReservation(id))
}