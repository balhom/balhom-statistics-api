package org.balhom.statisticsapi.modules.currencyprofilechanges.domain.props

import org.balhom.statisticsapi.common.data.enums.EventChangeTypeEnum
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.models.CurrencyProfileSharedUser
import java.util.*

data class CurrencyProfileChangeProps(
    val eventChangeTypeEnum: EventChangeTypeEnum,
    val currencyProfileId: UUID,
    val balance: Double,
    val monthlyGoal: Double,
    val yearlyGoal: Double,
    val ownerId: UUID,
    val sharedUsers: MutableList<CurrencyProfileSharedUser>,
)
