package org.balhom.statisticsapi.modules.statistics.domain.props

import org.balhom.statisticsapi.common.data.enums.EventChangeTypeEnum
import org.balhom.statisticsapi.common.utils.TestDataUtils.Companion.randomBigDecimal
import org.balhom.statisticsapi.common.utils.TestDataUtils.Companion.randomPastDateTime
import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum
import org.balhom.statisticsapi.modules.transactionchanges.domain.props.TransactionChangeProps
import org.balhom.statisticsapi.modules.transactionchanges.domain.props.TransactionOldDataProps
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

class MockTransactionChangePropsFactory {
    companion object {
        fun create(
            eventChangeType: EventChangeTypeEnum,
            type: TransactionTypeEnum = TransactionTypeEnum.entries.random(),
            amount: BigDecimal = randomBigDecimal(1.0, 1000.0),
            currencyProfileId: UUID = UUID.randomUUID(),
            userId: UUID = UUID.randomUUID(),
        ): TransactionChangeProps {

            val oldDate: LocalDateTime? = if (
                eventChangeType == EventChangeTypeEnum.UPDATE
            ) randomPastDateTime() else null

            val oldCategory: String? = if (
                eventChangeType == EventChangeTypeEnum.UPDATE
            ) "Category_${UUID.randomUUID().toString().take(8)}" else null

            val oldAmount: BigDecimal? = if (
                eventChangeType == EventChangeTypeEnum.UPDATE
            ) randomBigDecimal(1.0, 1000.0) else null

            return TransactionChangeProps(
                eventChangeType = eventChangeType,
                id = UUID.randomUUID(),
                type = type,
                date = randomPastDateTime(),
                category = "Category_${UUID.randomUUID().toString().take(8)}",
                amount = amount,
                oldData = if (
                    oldDate == null
                    || oldCategory == null
                    || oldAmount == null
                ) null else TransactionOldDataProps(
                    oldDate = oldDate,
                    oldCategory = oldCategory,
                    oldAmount = oldAmount
                ),
                cpGoalMonthlySaving = randomBigDecimal(100.0, 2000.0),
                cpGoalYearlySaving = randomBigDecimal(1000.0, 20000.0),
                currencyProfileId = currencyProfileId,
                userId = userId
            )
        }
    }
}
