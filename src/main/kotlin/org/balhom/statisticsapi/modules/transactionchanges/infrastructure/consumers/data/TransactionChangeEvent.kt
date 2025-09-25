package org.balhom.statisticsapi.modules.transactionchanges.infrastructure.consumers.data

import io.quarkus.runtime.annotations.RegisterForReflection
import org.balhom.statisticsapi.common.data.enums.EventChangeTypeEnum
import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum
import org.balhom.statisticsapi.modules.transactionchanges.domain.props.TransactionChangeProps
import org.balhom.statisticsapi.modules.transactionchanges.domain.props.TransactionOldDataProps
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@RegisterForReflection
data class TransactionChangeEvent(
    var action: String,
    var id: UUID,
    var type: TransactionTypeEnum,
    var date: LocalDateTime,
    var oldDate: LocalDateTime?,
    var category: String,
    var oldCategory: String?,
    var amount: BigDecimal,
    var oldAmount: BigDecimal?,
    var cpGoalMonthlySaving: BigDecimal,
    var cpGoalYearlySaving: BigDecimal,
    var currencyProfileId: UUID,
    var userId: UUID,
) {
    fun toChangeProps(): TransactionChangeProps {
        return TransactionChangeProps(
            eventChangeType = EventChangeTypeEnum
                .fromAction(action),
            id = id,
            type = type,
            date = date,
            category = category,
            amount = amount,
            oldData = if (
                oldDate == null
                || oldCategory == null
                || oldAmount == null
            ) null else TransactionOldDataProps(
                oldDate = oldDate !!,
                oldCategory = oldCategory !!,
                oldAmount = oldAmount !!
            ),
            cpGoalMonthlySaving = cpGoalMonthlySaving,
            cpGoalYearlySaving = cpGoalYearlySaving,
            currencyProfileId = currencyProfileId,
            userId = userId,
        )
    }
}
