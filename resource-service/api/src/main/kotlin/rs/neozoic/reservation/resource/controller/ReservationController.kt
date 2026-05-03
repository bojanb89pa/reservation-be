package rs.neozoic.reservation.resource.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import rs.neozoic.reservation.domain.model.AuthenticatedUser
import rs.neozoic.reservation.domain.model.Reservation
import rs.neozoic.reservation.domain.usecase.reservation.CreateReservationUseCase
import rs.neozoic.reservation.domain.usecase.reservation.GetReservationUseCase
import java.util.*

@Tag(name = "Reservations", description = "Reservation booking and retrieval")
@RestController
@RequestMapping("/api/resources/{resourceId}/reservations")
class ReservationController(
    private val createReservationUseCase: CreateReservationUseCase,
    private val getReservationUseCase: GetReservationUseCase
) {

    @Operation(summary = "Book a reservation for a resource")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Reservation created"),
        ApiResponse(responseCode = "400", description = "Resource not found or slot outside availability"),
        ApiResponse(responseCode = "401", description = "Authentication required")
    )
    @PostMapping
    fun createReservation(
        @PathVariable resourceId: UUID,
        @AuthenticationPrincipal authUser: AuthenticatedUser,
        @RequestBody reservation: Reservation
    ): ResponseEntity<Reservation> =
        ResponseEntity.ok(createReservationUseCase(authUser.id, resourceId, reservation))

    @Operation(summary = "Get a reservation by its ID")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Reservation found"),
        ApiResponse(responseCode = "401", description = "Authentication required")
    )
    @GetMapping("/{id}")
    fun getReservation(@PathVariable id: UUID): ResponseEntity<Reservation> =
        ResponseEntity.ok(getReservationUseCase(id))
}
