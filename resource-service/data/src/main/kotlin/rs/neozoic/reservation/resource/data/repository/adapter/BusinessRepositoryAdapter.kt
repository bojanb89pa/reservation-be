package rs.neozoic.reservation.resource.data.repository.adapter

import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import rs.neozoic.reservation.domain.model.Business
import rs.neozoic.reservation.domain.model.PageRequest
import rs.neozoic.reservation.domain.model.PageResponse
import rs.neozoic.reservation.domain.port.BusinessRepositoryPort
import rs.neozoic.reservation.resource.data.model.mapper.toDomain
import rs.neozoic.reservation.resource.data.model.mapper.toEntity
import rs.neozoic.reservation.resource.data.repository.BusinessRepository
import java.util.*
import org.springframework.data.domain.PageRequest as SpringPageRequest

@Repository
class BusinessRepositoryAdapter(
    private val businessRepository: BusinessRepository
) : BusinessRepositoryPort {
    override fun createBusiness(business: Business): Business =
        businessRepository.save(business.toEntity()).toDomain()

    override fun getBusinessByPublicId(publicId: UUID) =
        businessRepository.findByPublicId(publicId).toDomain()

    override fun existByPublicId(publicId: UUID) =
        businessRepository.existsByPublicId(publicId)

    override fun findAllBusinesses(pageRequest: PageRequest): PageResponse<Business> =
        when (pageRequest) {
            is PageRequest.Offset -> {
                val pageable = SpringPageRequest.of(pageRequest.page, pageRequest.size, Sort.by("id"))
                val page = businessRepository.findAll(pageable)
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
                val page = businessRepository.findAll(pageable)
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