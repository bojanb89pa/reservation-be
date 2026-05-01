package rs.neozoic.reservation.resource.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.usecase.business.CreateBusinessUseCase
import rs.neozoic.reservation.domain.usecase.business.GetBusinessUseCase
import java.util.*

@RestController
@RequestMapping("/api/businesses")
class BusinessController(
    private val createBusinessUseCase: CreateBusinessUseCase,
    private val getBusinessUseCase: GetBusinessUseCase
) {
    @PostMapping
    fun createBusiness(@RequestBody business: Business): ResponseEntity<Business> =
        ResponseEntity.ok(createBusinessUseCase(business))

    @GetMapping("/{id}")
    fun getBusiness(@PathVariable id: UUID): ResponseEntity<Business> =
        ResponseEntity.ok(getBusinessUseCase(id))
}
