package org.balhom.statisticsapi.modules.statistics.domain.models

import java.math.BigDecimal
import java.util.*

data class MonthlyTransactionStatistic(
    var currencyProfileId: UUID,
    var month: Int,
    var year: Int,
    var income: BigDecimal,
    var expenses: BigDecimal,
)
