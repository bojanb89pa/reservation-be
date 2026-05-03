package rs.neozoic.reservation.resource.data.model.entity

import jakarta.persistence.*
import rs.neozoic.reservation.domain.model.ResourceType
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "resources")
data class ResourceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "public_id", nullable = false, unique = true)
    val publicId: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    val business: BusinessEntity,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    val type: ResourceType,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    @Version
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
