package org.balhom.statisticsapi.modules.statistics.domain.repositories

import io.smallrye.mutiny.Uni
import org.balhom.statisticsapi.modules.statistics.domain.models.MonthlySavingsStatistic
import java.util.*

interface MonthlySavingsStatisticRepository {
    fun findAllByCurrencyProfileIdAndYear(
        currencyProfileId: UUID,
        year: Int
    ): Uni<List<MonthlySavingsStatistic>>

    fun findByCurrencyProfileIdAndMonthAndYear(
        currencyProfileId: UUID,
        month: Int,
        year: Int
    ): MonthlySavingsStatistic

    fun save(statistic: MonthlySavingsStatistic):
            MonthlySavingsStatistic

    fun deleteAllByCurrencyProfileId(currencyProfileId: UUID)
}
