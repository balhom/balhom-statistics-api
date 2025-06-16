package org.balhom.statisticsapi.modules.currencyprofilechanges.infrastructure.consumers.data

import io.quarkus.runtime.annotations.RegisterForReflection
import org.balhom.statisticsapi.common.data.enums.EventChangeTypeEnum
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.models.CurrencyProfileSharedUser
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.props.CurrencyProfileChangeProps
import java.math.BigDecimal
import java.util.UUID

@RegisterForReflection
data class CurrencyProfileChangeEvent(
    val action: String,
    val id: UUID,
    val balance: BigDecimal,
    val monthlyGoal: BigDecimal,
    val yearlyGoal: BigDecimal,
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
