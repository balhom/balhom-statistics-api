package org.balhom.statisticsapi.modules.statistics.domain.models

import java.math.BigDecimal
import java.util.*

data class YearlySavingsStatistic(
    var currencyProfileId: UUID,
    var year: Int,
    var savings: BigDecimal,
    var goal: BigDecimal,
)
