package org.balhom.statisticsapi.modules.statistics.domain.props

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class TransactionStatisticToAddProps(
    var currencyProfileId: UUID,
    var date: LocalDateTime,
    var oldDate: LocalDateTime?,
    var category: String,
    var oldCategory: String?,
    var incomeToAdd: BigDecimal,
    var oldIncomeAdded: BigDecimal?,
    var expensesToAdd: BigDecimal,
    var oldExpensesAdded: BigDecimal?,
)
