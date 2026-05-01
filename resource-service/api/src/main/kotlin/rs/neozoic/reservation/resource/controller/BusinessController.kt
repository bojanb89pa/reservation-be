package rs.neozoic.reservation.resource.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.usecase.business.CreateBusinessUseCase
import rs.neozoic.reservation.domain.usecase.business.GetBusinessUseCase
import java.util.*

@Tag(name = "Businesses", description = "Business management")
@RestController
@RequestMapping("/api/businesses")
class BusinessController(
    private val createBusinessUseCase: CreateBusinessUseCase,
    private val getBusinessUseCase: GetBusinessUseCase
) {

    @Operation(summary = "Create a new business")
    @ApiResponse(responseCode = "200", description = "Business created")
    @PostMapping
    fun createBusiness(@RequestBody business: Business): ResponseEntity<Business> =
        ResponseEntity.ok(createBusinessUseCase(business))

    @Operation(summary = "Get a business by its public ID")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Business found"),
        ApiResponse(responseCode = "404", description = "Business not found")
    )
    @GetMapping("/{id}")
    fun getBusiness(@PathVariable id: UUID): ResponseEntity<Business> =
        ResponseEntity.ok(getBusinessUseCase(id))
}
