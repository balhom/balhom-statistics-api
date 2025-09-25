package org.balhom.statisticsapi.modules.statistics.domain.props

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class SavingsStatisticToAddProps(
    var currencyProfileId: UUID,
    var date: LocalDateTime,
    var oldDate: LocalDateTime?,
    var amountToAdd: BigDecimal,
    var oldAmountAdded: BigDecimal?,
    var monthlyGoal: BigDecimal,
    var yearlyGoal: BigDecimal,
)
