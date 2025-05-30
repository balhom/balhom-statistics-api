package org.balhom.statisticsapi.modules.statistics.domain.repositories

import io.smallrye.mutiny.Uni
import org.balhom.statisticsapi.modules.statistics.domain.models.MonthlyTransactionStatistic
import java.util.*

interface MonthlyTransactionStatisticRepository {
    fun findAllByCurrencyProfileIdAndYear(
        currencyProfileId: UUID,
        year: Int
    ): Uni<List<MonthlyTransactionStatistic>>

    fun findByCurrencyProfileIdAndMonthAndYear(
        currencyProfileId: UUID,
        month: Int,
        year: Int
    ): MonthlyTransactionStatistic

    fun save(statistic: MonthlyTransactionStatistic):
            MonthlyTransactionStatistic

    fun deleteAllByCurrencyProfileId(currencyProfileId: UUID)
}
