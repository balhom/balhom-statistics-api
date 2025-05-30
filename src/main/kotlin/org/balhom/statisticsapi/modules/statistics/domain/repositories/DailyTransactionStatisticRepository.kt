package org.balhom.statisticsapi.modules.statistics.domain.repositories

import io.smallrye.mutiny.Uni
import org.balhom.statisticsapi.modules.statistics.domain.models.DailyTransactionStatistic
import java.util.*

interface DailyTransactionStatisticRepository {
    fun findAllByCurrencyProfileIdAndMonthAndYear(
        currencyProfileId: UUID,
        month: Int,
        year: Int
    ): Uni<List<DailyTransactionStatistic>>

    fun findByCurrencyProfileIdAndDayAndMonthAndYear(
        currencyProfileId: UUID,
        day: Int,
        month: Int,
        year: Int
    ): DailyTransactionStatistic

    fun save(statistic: DailyTransactionStatistic):
            DailyTransactionStatistic

    fun deleteAllByCurrencyProfileId(currencyProfileId: UUID)
}
