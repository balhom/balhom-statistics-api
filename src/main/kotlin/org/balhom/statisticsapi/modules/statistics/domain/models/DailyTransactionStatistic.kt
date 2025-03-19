package org.balhom.statisticsapi.modules.statistics.domain.models

data class DailyTransactionStatistic(
    var day: Int,
    var income: Double,
    var expenses: Double,
)
