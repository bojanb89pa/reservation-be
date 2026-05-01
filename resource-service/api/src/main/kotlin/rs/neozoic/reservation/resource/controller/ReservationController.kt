package rs.neozoic.reservation.resource.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import rs.neozoic.reservation.domain.model.AuthenticatedUser
import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.domain.usecase.reservation.CreateReservationUseCase
import rs.neozoic.reservation.domain.usecase.reservation.GetReservationUseCase
import java.util.*

@RestController
@RequestMapping("/api/businesses/{businessId}/reservations")
class ReservationController(
    private val createReservationUseCase: CreateReservationUseCase,
    private val getReservationUseCase: GetReservationUseCase
) {
    @PostMapping
    fun createReservation(
        @PathVariable businessId: UUID,
        @AuthenticationPrincipal authUser: AuthenticatedUser,
        @RequestBody reservation: Reservation
    ): ResponseEntity<Reservation> =
        ResponseEntity.ok(createReservationUseCase(authUser.id, businessId, reservation))

    @GetMapping("/{id}")
    fun getReservation(@PathVariable id: UUID): ResponseEntity<Reservation> =
        ResponseEntity.ok(getReservationUseCase(id))
}
