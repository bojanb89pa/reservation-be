package rs.neozoic.reservation.resource.data.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import rs.neozoic.reservation.resource.data.model.entity.ResourceEntity
import java.util.*

interface ResourceRepository : JpaRepository<ResourceEntity, Long> {
    fun findByPublicId(publicId: UUID): ResourceEntity?
    fun existsByPublicId(publicId: UUID): Boolean
    fun findAllByBusinessPublicId(businessPublicId: UUID, pageable: Pageable): Page<ResourceEntity>
}
