package org.balhom.statisticsapi.modules.statistics.domain.repositories

import io.smallrye.mutiny.Uni
import org.balhom.statisticsapi.modules.statistics.domain.models.YearlySavingsStatistic
import java.util.*

interface YearlySavingsStatisticRepository {
    fun findAllByCurrencyProfileId(
        currencyProfileId: UUID
    ): Uni<List<YearlySavingsStatistic>>

    fun findByCurrencyProfileIdAndYear(
        currencyProfileId: UUID,
        year: Int
    ): YearlySavingsStatistic

    fun save(statistic: YearlySavingsStatistic):
            YearlySavingsStatistic

    fun deleteAllByCurrencyProfileId(currencyProfileId: UUID)
}
