package org.balhom.statisticsapi.modules.transactionchanges.domain.props

import org.balhom.statisticsapi.common.data.enums.EventChangeTypeEnum
import java.util.UUID

data class TransactionChangeProps(
    val eventChangeTypeEnum: EventChangeTypeEnum,
    val transactionId: UUID,
    val currencyProfileId: UUID,
    val userId: UUID,
    val sum: Double,
)
