package org.balhom.statisticsapi.modules.transactionchanges.domain.props

import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionOldDataProps(
    var oldDate: LocalDateTime,
    var oldCategory: String,
    var oldAmount: BigDecimal,
)
