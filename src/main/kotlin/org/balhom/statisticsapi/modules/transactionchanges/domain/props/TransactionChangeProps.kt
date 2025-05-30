package org.balhom.statisticsapi.modules.transactionchanges.domain.props

import org.balhom.statisticsapi.common.data.enums.EventChangeTypeEnum
import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class TransactionChangeProps(
    val eventChangeTypeEnum: EventChangeTypeEnum,
    val id: UUID,
    var type: TransactionTypeEnum,
    val date: LocalDateTime,
    val currencyProfileId: UUID,
    val userId: UUID,
    val sum: BigDecimal,
    var cpGoalMonthlySaving: BigDecimal,
    var cpGoalYearlySaving: BigDecimal,
)
