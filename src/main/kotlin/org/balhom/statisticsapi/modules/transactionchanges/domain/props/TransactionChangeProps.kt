package org.balhom.statisticsapi.modules.transactionchanges.domain.props

import org.balhom.statisticsapi.common.data.enums.EventChangeTypeEnum
import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class TransactionChangeProps(
    val eventChangeType: EventChangeTypeEnum,
    val id: UUID,
    var type: TransactionTypeEnum,
    val date: LocalDateTime,
    var oldDate: LocalDateTime?,
    var category: String,
    var oldCategory: String?,
    var amount: BigDecimal,
    var oldAmount: BigDecimal?,
    var cpGoalMonthlySaving: BigDecimal,
    var cpGoalYearlySaving: BigDecimal,
    val currencyProfileId: UUID,
    val userId: UUID,
)
