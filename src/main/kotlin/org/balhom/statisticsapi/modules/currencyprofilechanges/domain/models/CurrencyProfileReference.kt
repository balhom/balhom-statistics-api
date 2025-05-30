package org.balhom.statisticsapi.modules.currencyprofilechanges.domain.models

import java.math.BigDecimal
import java.util.*

data class CurrencyProfileReference(
    var id: UUID,
    var balance: BigDecimal,
    var goalMonthlySaving: BigDecimal,
    var goalYearlySaving: BigDecimal,
    var sharedUsers: List<CurrencyProfileSharedUser>,
    var userId: UUID
)
