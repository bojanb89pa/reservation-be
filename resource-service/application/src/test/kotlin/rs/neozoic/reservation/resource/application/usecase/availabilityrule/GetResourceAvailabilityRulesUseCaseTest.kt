package rs.neozoic.reservation.resource.application.usecase.availabilityrule

import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import rs.neozoic.reservation.domain.model.ResourceAvailabilityRule
import rs.neozoic.reservation.domain.port.ResourceAvailabilityRuleRepositoryPort
import rs.neozoic.reservation.domain.port.ResourceRepositoryPort
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetResourceAvailabilityRulesUseCaseTest {

    private val ruleRepository: ResourceAvailabilityRuleRepositoryPort = mock()
    private val resourceRepository: ResourceRepositoryPort = mock()
    private val useCase = GetResourceAvailabilityRulesUseCaseImpl(ruleRepository, resourceRepository)

    private fun rule(resourceId: UUID) = ResourceAvailabilityRule(
        id = UUID.randomUUID(),
        resourceId = resourceId,
        dayOfWeek = DayOfWeek.WEDNESDAY,
        startTime = LocalTime.of(9, 0),
        endTime = LocalTime.of(18, 0),
    )

    @Test
    fun `invoke returns rules when resource exists`() {
        val resourceId = UUID.randomUUID()
        val rules = listOf(rule(resourceId), rule(resourceId))

        whenever(resourceRepository.existsByPublicId(resourceId)).thenReturn(true)
        whenever(ruleRepository.findAllByResourceId(resourceId)).thenReturn(rules)

        val result = useCase(resourceId)

        assertEquals(rules, result)
        verify(resourceRepository).existsByPublicId(resourceId)
        verify(ruleRepository).findAllByResourceId(resourceId)
    }

    @Test
    fun `invoke returns empty list when resource has no rules`() {
        val resourceId = UUID.randomUUID()

        whenever(resourceRepository.existsByPublicId(resourceId)).thenReturn(true)
        whenever(ruleRepository.findAllByResourceId(resourceId)).thenReturn(emptyList())

        val result = useCase(resourceId)

        assertEquals(emptyList(), result)
    }

    @Test
    fun `invoke throws IllegalArgumentException when resource not found`() {
        val resourceId = UUID.randomUUID()

        whenever(resourceRepository.existsByPublicId(resourceId)).thenReturn(false)

        assertFailsWith<IllegalArgumentException> {
            useCase(resourceId)
        }

        verify(ruleRepository, never()).findAllByResourceId(any())
    }
}
