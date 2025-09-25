package org.balhom.statisticsapi.modules.transactionchanges.domain.props

import org.balhom.statisticsapi.common.data.enums.EventChangeTypeEnum
import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class TransactionChangeProps(
    var eventChangeType: EventChangeTypeEnum,
    var id: UUID,
    var type: TransactionTypeEnum,
    var date: LocalDateTime,
    var category: String,
    var amount: BigDecimal,
    var oldData: TransactionOldDataProps?,
    var cpGoalMonthlySaving: BigDecimal,
    var cpGoalYearlySaving: BigDecimal,
    var currencyProfileId: UUID,
    var userId: UUID,
)
