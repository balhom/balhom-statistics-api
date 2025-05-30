package org.balhom.statisticsapi.modules.currencyprofilechanges.domain.props

import org.balhom.statisticsapi.common.data.enums.EventChangeTypeEnum
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.models.CurrencyProfileSharedUser
import java.math.BigDecimal
import java.util.*

data class CurrencyProfileChangeProps(
    val eventChangeTypeEnum: EventChangeTypeEnum,
    val currencyProfileId: UUID,
    val balance: BigDecimal,
    val monthlyGoal: BigDecimal,
    val yearlyGoal: BigDecimal,
    val ownerId: UUID,
    val sharedUsers: MutableList<CurrencyProfileSharedUser>,
)
