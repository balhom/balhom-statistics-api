package org.balhom.statisticsapi.modules.transactionchanges.infrastructure.consumers.data

import org.balhom.statisticsapi.common.data.enums.EventChangeTypeEnum
import org.balhom.statisticsapi.modules.transactionchanges.domain.props.TransactionChangeProps
import java.util.UUID

data class TransactionChangeEvent(
    val action: String,
    val id: UUID,
    val amount: Double,
    val oldAmount: Double?,
    val currencyProfileId: UUID,
    val userId: UUID,
) {
    fun toChangeProps(): TransactionChangeProps {
        return TransactionChangeProps(
            EventChangeTypeEnum.fromAction(action),
            id,
            currencyProfileId,
            userId,
            amount - (oldAmount ?: 0.0)
        )
    }
}
