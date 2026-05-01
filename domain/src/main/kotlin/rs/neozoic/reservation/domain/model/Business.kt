package rs.neozoic.reservation.domain.model

import java.util.*

/**
 * Represents a business that offers reservable services.
 *
 * @property id public identifier assigned on persistence; null before the entity is saved.
 * @property name display name of the business.
 */
data class Business(
    val id: UUID?,
    val name: String
)
