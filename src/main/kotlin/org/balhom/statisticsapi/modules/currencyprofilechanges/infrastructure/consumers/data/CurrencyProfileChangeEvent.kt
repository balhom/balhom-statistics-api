package org.balhom.statisticsapi.modules.currencyprofilechanges.infrastructure.consumers.data

import org.balhom.statisticsapi.common.data.enums.EventChangeTypeEnum
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.models.CurrencyProfileSharedUser
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.props.CurrencyProfileChangeProps
import java.util.UUID

data class CurrencyProfileChangeEvent(
    val action: String,
    val id: UUID,
    val balance: Double,
    val monthlyGoal: Double,
    val yearlyGoal: Double,
    val ownerId: UUID,
    val sharedUsers: MutableList<CurrencyProfileSharedUser>,
) {
    fun toChangeProps(): CurrencyProfileChangeProps {
        return CurrencyProfileChangeProps(
            EventChangeTypeEnum.fromAction(action),
            id,
            balance,
            monthlyGoal,
            yearlyGoal,
            ownerId,
            sharedUsers
        )
    }
}
