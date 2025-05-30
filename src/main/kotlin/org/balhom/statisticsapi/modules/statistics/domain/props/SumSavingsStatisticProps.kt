package org.balhom.statisticsapi.modules.statistics.domain.props

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class SumSavingsStatisticProps(
    val currencyProfileId: UUID,
    val date: LocalDateTime,
    val sum: BigDecimal,
    val monthlyGoal: BigDecimal,
    val yearlyGoal: BigDecimal,
)
