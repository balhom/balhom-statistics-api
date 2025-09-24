package org.balhom.statisticsapi.modules.statistics.domain.props

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class GoalSavingsStatisticToUpdateProps(
    val currencyProfileId: UUID,
    val date: LocalDateTime,
    val monthlyGoal: BigDecimal,
    val yearlyGoal: BigDecimal,
)
