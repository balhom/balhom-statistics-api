package org.balhom.statisticsapi.modules.statistics.domain.models

data class MonthlyTransactionStatistic(
    var month: Int,
    var income: Double,
    var expenses: Double,
)
