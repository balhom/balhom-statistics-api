package org.balhom.statisticsapi.modules.statistics.domain.props

import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class SumTransactionStatisticProps(
    val currencyProfileId: UUID,
    val type: TransactionTypeEnum,
    val date: LocalDateTime,
    val category: String,
    val sumIncome: BigDecimal,
    val sumExpenses: BigDecimal,
)
