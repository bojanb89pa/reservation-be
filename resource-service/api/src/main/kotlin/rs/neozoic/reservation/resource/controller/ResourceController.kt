package rs.neozoic.reservation.resource.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rs.neozoic.reservation.domain.model.PageRequest
import rs.neozoic.reservation.domain.model.PageResponse
import rs.neozoic.reservation.domain.model.Resource
import rs.neozoic.reservation.domain.usecase.resource.CreateResourceUseCase
import rs.neozoic.reservation.domain.usecase.resource.GetAllResourcesUseCase
import rs.neozoic.reservation.domain.usecase.resource.GetResourceUseCase
import java.util.*

@Tag(name = "Resources", description = "Resource management within a business")
@RestController
class ResourceController(
    private val createResourceUseCase: CreateResourceUseCase,
    private val getResourceUseCase: GetResourceUseCase,
    private val getAllResourcesUseCase: GetAllResourcesUseCase
) {

    @Operation(summary = "Create a new resource for a business")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Resource created"),
        ApiResponse(responseCode = "400", description = "Business not found")
    )
    @PostMapping("/api/businesses/{businessId}/resources")
    fun createResource(
        @PathVariable businessId: UUID,
        @RequestBody resource: Resource
    ): ResponseEntity<Resource> =
        ResponseEntity.ok(createResourceUseCase(businessId, resource))

    @Operation(summary = "List all resources for a business (paginated)")
    @ApiResponse(responseCode = "200", description = "Page of resources returned")
    @GetMapping("/api/businesses/{businessId}/resources")
    fun getAllResources(
        @PathVariable businessId: UUID,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<PageResponse<Resource>> =
        ResponseEntity.ok(getAllResourcesUseCase(businessId, PageRequest.Offset(page, size)))

    @Operation(summary = "Get a resource by its ID")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Resource found"),
        ApiResponse(responseCode = "404", description = "Resource not found")
    )
    @GetMapping("/api/resources/{id}")
    fun getResource(@PathVariable id: UUID): ResponseEntity<Resource> {
        val resource = getResourceUseCase(id)
        return resource?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }
}
