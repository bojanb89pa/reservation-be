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

class CreateResourceAvailabilityRuleUseCaseTest {

    private val ruleRepository: ResourceAvailabilityRuleRepositoryPort = mock()
    private val resourceRepository: ResourceRepositoryPort = mock()
    private val useCase = CreateResourceAvailabilityRuleUseCaseImpl(ruleRepository, resourceRepository)

    private fun rule(
        resourceId: UUID = UUID.randomUUID(),
        startTime: LocalTime = LocalTime.of(9, 0),
        endTime: LocalTime = LocalTime.of(17, 0),
    ) = ResourceAvailabilityRule(
        id = null,
        resourceId = resourceId,
        dayOfWeek = DayOfWeek.MONDAY,
        startTime = startTime,
        endTime = endTime,
    )

    @Test
    fun `invoke creates rule when resource exists and times are valid`() {
        val resourceId = UUID.randomUUID()
        val input = rule(resourceId = resourceId)
        val saved = input.copy(id = UUID.randomUUID())

        whenever(resourceRepository.existsByPublicId(resourceId)).thenReturn(true)
        whenever(ruleRepository.createRule(input)).thenReturn(saved)

        val result = useCase(resourceId, input)

        assertEquals(saved, result)
        verify(resourceRepository).existsByPublicId(resourceId)
        verify(ruleRepository).createRule(input)
    }

    @Test
    fun `invoke throws IllegalArgumentException when resource not found`() {
        val resourceId = UUID.randomUUID()
        val input = rule(resourceId = resourceId)

        whenever(resourceRepository.existsByPublicId(resourceId)).thenReturn(false)

        assertFailsWith<IllegalArgumentException> {
            useCase(resourceId, input)
        }

        verify(ruleRepository, never()).createRule(any())
    }

    @Test
    fun `invoke throws IllegalArgumentException when startTime is not before endTime`() {
        val resourceId = UUID.randomUUID()
        val input = rule(resourceId = resourceId, startTime = LocalTime.of(17, 0), endTime = LocalTime.of(9, 0))

        whenever(resourceRepository.existsByPublicId(resourceId)).thenReturn(true)

        assertFailsWith<IllegalArgumentException> {
            useCase(resourceId, input)
        }

        verify(ruleRepository, never()).createRule(any())
    }

    @Test
    fun `invoke throws IllegalArgumentException when startTime equals endTime`() {
        val resourceId = UUID.randomUUID()
        val time = LocalTime.of(10, 0)
        val input = rule(resourceId = resourceId, startTime = time, endTime = time)

        whenever(resourceRepository.existsByPublicId(resourceId)).thenReturn(true)

        assertFailsWith<IllegalArgumentException> {
            useCase(resourceId, input)
        }

        verify(ruleRepository, never()).createRule(any())
    }
}
