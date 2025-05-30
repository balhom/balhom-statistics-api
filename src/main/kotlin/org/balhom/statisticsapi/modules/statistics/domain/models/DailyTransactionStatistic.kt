package org.balhom.statisticsapi.modules.statistics.domain.models

import java.math.BigDecimal
import java.util.UUID

data class DailyTransactionStatistic(
    var currencyProfileId: UUID,
    var day: Int,
    var month: Int,
    var year: Int,
    var income: BigDecimal,
    var expenses: BigDecimal,
)
