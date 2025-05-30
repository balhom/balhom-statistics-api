package org.balhom.statisticsapi.modules.transactionchanges.infrastructure.consumers.data

import io.quarkus.runtime.annotations.RegisterForReflection
import org.balhom.statisticsapi.common.data.enums.EventChangeTypeEnum
import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum
import org.balhom.statisticsapi.modules.transactionchanges.domain.props.TransactionChangeProps
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@RegisterForReflection
data class TransactionChangeEvent(
    var action: String,
    var id: UUID,
    var type: TransactionTypeEnum,
    var date: LocalDateTime,
    var amount: BigDecimal,
    var oldAmount: BigDecimal?,
    var cpGoalMonthlySaving: BigDecimal,
    var cpGoalYearlySaving: BigDecimal,
    var currencyProfileId: UUID,
    var userId: UUID,
) {
    fun toChangeProps(): TransactionChangeProps {
        return TransactionChangeProps(
            EventChangeTypeEnum.fromAction(action),
            id,
            type,
            date,
            currencyProfileId,
            userId,
            amount - (oldAmount ?: BigDecimal(0.0)),
            cpGoalMonthlySaving,
            cpGoalYearlySaving,
        )
    }
}
