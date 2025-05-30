package org.balhom.statisticsapi.modules.statistics.domain.props

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class SumTransactionStatisticProps(
    val currencyProfileId: UUID,
    val date: LocalDateTime,
    val sumIncome: BigDecimal,
    val sumExpenses: BigDecimal,
)
