package rs.neozoic.reservation.resource.data.model.entity

import jakarta.persistence.*
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.*

@Entity
@Table(name = "resource_availability_rules")
data class ResourceAvailabilityRuleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "public_id", nullable = false, unique = true)
    val publicId: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", nullable = false)
    val resource: ResourceEntity,

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    val dayOfWeek: DayOfWeek,

    @Column(name = "start_time", nullable = false)
    val startTime: LocalTime,

    @Column(name = "end_time", nullable = false)
    val endTime: LocalTime,
)
