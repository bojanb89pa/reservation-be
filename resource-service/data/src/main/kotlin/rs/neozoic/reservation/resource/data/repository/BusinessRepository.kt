package rs.neozoic.reservation.resource.data.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rs.neozoic.reservation.resource.data.model.entity.BusinessEntity
import java.util.*

@Repository
interface BusinessRepository: JpaRepository<BusinessEntity, Long> {
    fun findByPublicId(publicId: UUID): BusinessEntity
    fun existsByPublicId(publicId: UUID): Boolean
}