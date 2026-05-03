package rs.neozoic.reservation.resource.application.usecase.availabilityrule

import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import rs.neozoic.reservation.domain.model.ResourceAvailabilityRule
import rs.neozoic.reservation.domain.port.ResourceAvailabilityRuleRepositoryPort
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertFailsWith

class DeleteResourceAvailabilityRuleUseCaseTest {

    private val ruleRepository: ResourceAvailabilityRuleRepositoryPort = mock()
    private val useCase = DeleteResourceAvailabilityRuleUseCaseImpl(ruleRepository)

    private fun rule(id: UUID) = ResourceAvailabilityRule(
        id = id,
        resourceId = UUID.randomUUID(),
        dayOfWeek = DayOfWeek.TUESDAY,
        startTime = LocalTime.of(8, 0),
        endTime = LocalTime.of(16, 0),
    )

    @Test
    fun `invoke deletes rule when it exists`() {
        val ruleId = UUID.randomUUID()
        whenever(ruleRepository.getRuleByPublicId(ruleId)).thenReturn(rule(ruleId))

        useCase(ruleId)

        verify(ruleRepository).getRuleByPublicId(ruleId)
        verify(ruleRepository).deleteRule(ruleId)
    }

    @Test
    fun `invoke throws IllegalArgumentException when rule not found`() {
        val ruleId = UUID.randomUUID()
        whenever(ruleRepository.getRuleByPublicId(ruleId)).thenReturn(null)

        assertFailsWith<IllegalArgumentException> {
            useCase(ruleId)
        }

        verify(ruleRepository, never()).deleteRule(ruleId)
    }
}
