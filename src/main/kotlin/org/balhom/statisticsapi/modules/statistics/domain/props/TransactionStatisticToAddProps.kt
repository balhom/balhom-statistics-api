package org.balhom.statisticsapi.modules.statistics.domain.props

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class TransactionStatisticToAddProps(
    val currencyProfileId: UUID,
    val date: LocalDateTime,
    val oldDate: LocalDateTime?,
    val category: String,
    val oldCategory: String?,
    val incomeToAdd: BigDecimal,
    val oldIncomeAdded: BigDecimal?,
    val expensesToAdd: BigDecimal,
    val oldExpensesAdded: BigDecimal?,
)
