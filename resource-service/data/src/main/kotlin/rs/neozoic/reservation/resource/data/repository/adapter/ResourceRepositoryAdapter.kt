package rs.neozoic.reservation.resource.data.repository.adapter

import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import rs.neozoic.reservation.domain.model.PageRequest
import rs.neozoic.reservation.domain.model.PageResponse
import rs.neozoic.reservation.domain.model.Resource
import rs.neozoic.reservation.domain.port.ResourceRepositoryPort
import rs.neozoic.reservation.resource.data.model.mapper.toDomain
import rs.neozoic.reservation.resource.data.model.mapper.toEntity
import rs.neozoic.reservation.resource.data.repository.BusinessRepository
import rs.neozoic.reservation.resource.data.repository.ResourceRepository
import java.util.*
import org.springframework.data.domain.PageRequest as SpringPageRequest

@Repository
class ResourceRepositoryAdapter(
    private val resourceRepository: ResourceRepository,
    private val businessRepository: BusinessRepository
) : ResourceRepositoryPort {
    override fun createResource(resource: Resource): Resource {
        val business = businessRepository.findByPublicId(resource.businessId)
        return resourceRepository.save(resource.toEntity(business = business)).toDomain()
    }

    override fun getResourceByPublicId(publicId: UUID): Resource? =
        resourceRepository.findByPublicId(publicId)?.toDomain()

    override fun existsByPublicId(publicId: UUID): Boolean =
        resourceRepository.existsByPublicId(publicId)

    override fun findAllByBusinessId(businessId: UUID, pageRequest: PageRequest): PageResponse<Resource> =
        when (pageRequest) {
            is PageRequest.Offset -> {
                val pageable = SpringPageRequest.of(pageRequest.page, pageRequest.size, Sort.by("id"))
                val page = resourceRepository.findAllByBusinessPublicId(businessId, pageable)
                PageResponse(
                    content = page.content.map { it.toDomain() },
                    size = pageRequest.size,
                    page = pageRequest.page,
                    totalElements = page.totalElements,
                    totalPages = page.totalPages
                )
            }
            is PageRequest.Cursor -> {
                val decodedPage = pageRequest.cursor
                    ?.let { String(Base64.getDecoder().decode(it)).toIntOrNull() }
                    ?: 0
                val pageable = SpringPageRequest.of(decodedPage, pageRequest.size, Sort.by("id"))
                val page = resourceRepository.findAllByBusinessPublicId(businessId, pageable)
                PageResponse(
                    content = page.content.map { it.toDomain() },
                    size = pageRequest.size,
                    hasNext = page.hasNext(),
                    hasPrevious = page.hasPrevious(),
                    nextCursor = if (page.hasNext()) Base64.getEncoder().encodeToString("${decodedPage + 1}".toByteArray()) else null,
                    prevCursor = if (page.hasPrevious()) Base64.getEncoder().encodeToString("${decodedPage - 1}".toByteArray()) else null
                )
            }
        }
}
