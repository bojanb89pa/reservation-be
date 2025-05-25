package rs.neozoic.reservation.resource.data.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository
import rs.neozoic.reservation.resource.data.model.entity.BusinessEntity
import java.util.*

interface BusinessJpaRepository: JpaRepository<BusinessEntity, Long> {
    fun findByPublicId(publicId: UUID): BusinessEntity
    fun existsByPublicId(publicId: UUID): Boolean
}