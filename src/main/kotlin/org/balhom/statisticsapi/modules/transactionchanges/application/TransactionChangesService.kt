package org.balhom.statisticsapi.modules.transactionchanges.application

import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.common.data.enums.EventChangeTypeEnum
import org.balhom.statisticsapi.modules.statistics.application.SavingsStatisticsService
import org.balhom.statisticsapi.modules.statistics.application.TransactionStatisticsService
import org.balhom.statisticsapi.modules.statistics.domain.props.SavingsStatisticToAddProps
import org.balhom.statisticsapi.modules.statistics.domain.props.TransactionStatisticToAddProps
import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum
import org.balhom.statisticsapi.modules.transactionchanges.domain.props.TransactionChangeProps
import java.math.BigDecimal

@ApplicationScoped
class TransactionChangesService(
    private val transactionStatisticsService: TransactionStatisticsService,
    private val savingsStatisticsService: SavingsStatisticsService,
) {

    fun processChange(props: TransactionChangeProps) {
        // Old data can only be filled for update events
        if (props.eventChangeType != EventChangeTypeEnum.UPDATE) {
            props.oldData = null
        }

        val transactionStatisticToAddProps = if (props.type == TransactionTypeEnum.INCOME)
            TransactionStatisticToAddProps(
                currencyProfileId = props.currencyProfileId,
                date = props.date,
                oldDate = props.oldData?.oldDate,
                category = props.category,
                oldCategory = props.oldData?.oldCategory,
                incomeToAdd = props.amount,
                oldIncomeAdded = props.oldData?.oldAmount,
                expensesToAdd = BigDecimal(0.0),
                oldExpensesAdded = null
            ) else
            TransactionStatisticToAddProps(
                currencyProfileId = props.currencyProfileId,
                date = props.date,
                oldDate = props.oldData?.oldDate,
                category = props.category,
                oldCategory = props.oldData?.oldCategory,
                incomeToAdd = BigDecimal(0.0),
                oldIncomeAdded = null,
                expensesToAdd = props.amount,
                oldExpensesAdded = props.oldData?.oldAmount
            )

        val savingsStatisticToAddProps = SavingsStatisticToAddProps(
            currencyProfileId = props.currencyProfileId,
            date = props.date,
            oldDate = props.oldData?.oldDate,
            amountToAdd = if (
                props.type == TransactionTypeEnum.INCOME
            ) props.amount else props.amount.negate(),
            oldAmountAdded = if (
                props.type == TransactionTypeEnum.INCOME
                || props.oldData?.oldAmount == null
            ) props.oldData?.oldAmount else props.oldData?.oldAmount?.negate(),
            monthlyGoal = props.cpGoalMonthlySaving,
            yearlyGoal = props.cpGoalYearlySaving,
        )

        if (props.eventChangeType == EventChangeTypeEnum.DELETE) {
            transactionStatisticToAddProps.incomeToAdd = transactionStatisticToAddProps
                .incomeToAdd.negate()
            transactionStatisticToAddProps.expensesToAdd = transactionStatisticToAddProps
                .expensesToAdd.negate()

            savingsStatisticToAddProps.amountToAdd = savingsStatisticToAddProps
                .amountToAdd.negate()
        }

        transactionStatisticsService.add(
            transactionStatisticToAddProps
        )
        savingsStatisticsService.add(
            savingsStatisticToAddProps
        )
    }
}
