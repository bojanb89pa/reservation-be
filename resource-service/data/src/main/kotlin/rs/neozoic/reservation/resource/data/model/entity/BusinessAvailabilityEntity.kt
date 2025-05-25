package rs.neozoic.reservation.resource.data.model.entity

import jakarta.persistence.*
import java.time.DayOfWeek
import java.time.LocalTime

@Entity
@Table(name = "business_availabilities")
data class BusinessAvailabilityEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Enumerated(EnumType.STRING)
    val dayOfWeek: DayOfWeek, // MONDAY to SUNDAY

    val startTime: LocalTime,
    val endTime: LocalTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    val business: BusinessEntity
)