package org.balhom.statisticsapi.modules.statistics.domain.models

import java.math.BigDecimal
import java.util.*

data class MonthlySavingsStatistic(
    var currencyProfileId: UUID,
    var month: Int,
    var year: Int,
    var savings: BigDecimal,
    var goal: BigDecimal,
)
