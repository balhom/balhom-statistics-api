package org.balhom.statisticsapi.modules.statistics.domain.models

import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum
import java.math.BigDecimal
import java.util.*

data class CategoryTransactionStatistic(
    var currencyProfileId: UUID,
    var month: Int,
    var year: Int,
    var type: TransactionTypeEnum,
    var category: String,
    var value: BigDecimal,
)
