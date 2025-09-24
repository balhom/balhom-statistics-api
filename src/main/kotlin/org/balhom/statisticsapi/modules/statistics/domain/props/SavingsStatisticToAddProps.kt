package org.balhom.statisticsapi.modules.statistics.domain.props

import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class SavingsStatisticToAddProps(
    val currencyProfileId: UUID,
    val type: TransactionTypeEnum,
    val date: LocalDateTime,
    val oldDate: LocalDateTime?,
    var amountToAdd: BigDecimal,
    var oldAmountAdded: BigDecimal?,
    val monthlyGoal: BigDecimal,
    val yearlyGoal: BigDecimal,
)
