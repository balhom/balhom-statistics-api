package org.balhom.statisticsapi.modules.statistics.domain.repositories

import io.smallrye.mutiny.Uni
import org.balhom.statisticsapi.modules.statistics.domain.models.CategoryTransactionStatistic
import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum
import java.util.*

interface CategoryTransactionStatisticRepository {
    fun findAllByCurrencyProfileIdAndTypeAndMonthAndYear(
        currencyProfileId: UUID,
        type: TransactionTypeEnum,
        month: Int,
        year: Int
    ): Uni<List<CategoryTransactionStatistic>>

    fun findByCurrencyProfileIdAndTypeAndCategoryAndMonthAndYear(
        currencyProfileId: UUID,
        type: TransactionTypeEnum,
        category: String,
        month: Int,
        year: Int
    ): CategoryTransactionStatistic

    fun save(statistic: CategoryTransactionStatistic):
            CategoryTransactionStatistic

    fun deleteAllByCurrencyProfileId(currencyProfileId: UUID)
}
