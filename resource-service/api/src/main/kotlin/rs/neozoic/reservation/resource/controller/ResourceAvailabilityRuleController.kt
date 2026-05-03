package rs.neozoic.reservation.resource.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rs.neozoic.reservation.domain.model.ResourceAvailabilityRule
import rs.neozoic.reservation.domain.usecase.availabilityrule.CreateResourceAvailabilityRuleUseCase
import rs.neozoic.reservation.domain.usecase.availabilityrule.DeleteResourceAvailabilityRuleUseCase
import rs.neozoic.reservation.domain.usecase.availabilityrule.GetResourceAvailabilityRulesUseCase
import java.util.*

@Tag(name = "Availability Rules", description = "Weekly availability schedule for resources")
@RestController
@RequestMapping("/api/resources/{resourceId}/availability-rules")
class ResourceAvailabilityRuleController(
    private val createResourceAvailabilityRuleUseCase: CreateResourceAvailabilityRuleUseCase,
    private val getResourceAvailabilityRulesUseCase: GetResourceAvailabilityRulesUseCase,
    private val deleteResourceAvailabilityRuleUseCase: DeleteResourceAvailabilityRuleUseCase
) {

    @Operation(summary = "Add a weekly availability window to a resource")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Availability rule created"),
        ApiResponse(responseCode = "400", description = "Resource not found or invalid time range")
    )
    @PostMapping
    fun createAvailabilityRule(
        @PathVariable resourceId: UUID,
        @RequestBody rule: ResourceAvailabilityRule
    ): ResponseEntity<ResourceAvailabilityRule> =
        ResponseEntity.ok(createResourceAvailabilityRuleUseCase(resourceId, rule))

    @Operation(summary = "Get all availability rules for a resource")
    @ApiResponse(responseCode = "200", description = "List of availability rules")
    @GetMapping
    fun getAvailabilityRules(
        @PathVariable resourceId: UUID
    ): ResponseEntity<List<ResourceAvailabilityRule>> =
        ResponseEntity.ok(getResourceAvailabilityRulesUseCase(resourceId))

    @Operation(summary = "Delete an availability rule")
    @ApiResponses(
        ApiResponse(responseCode = "204", description = "Rule deleted"),
        ApiResponse(responseCode = "400", description = "Rule not found")
    )
    @DeleteMapping("/{ruleId}")
    fun deleteAvailabilityRule(
        @PathVariable resourceId: UUID,
        @PathVariable ruleId: UUID
    ): ResponseEntity<Void> {
        deleteResourceAvailabilityRuleUseCase(ruleId)
        return ResponseEntity.noContent().build()
    }
}
