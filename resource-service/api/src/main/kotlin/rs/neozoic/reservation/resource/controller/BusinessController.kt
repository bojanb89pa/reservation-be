package rs.neozoic.reservation.resource.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.service.BusinessService
import java.util.*

@RestController
@RequestMapping("/api/businesses")
class BusinessController(
    private val businessService: BusinessService
) {
    @PostMapping
    fun createBusiness(@RequestBody business: Business): ResponseEntity<Business> =
        ResponseEntity.ok(businessService.createBusiness(business))

    @GetMapping("/{id}")
    fun getBusiness(@PathVariable id: UUID): ResponseEntity<Business> =
        ResponseEntity.ok(businessService.getBusiness(id))
}