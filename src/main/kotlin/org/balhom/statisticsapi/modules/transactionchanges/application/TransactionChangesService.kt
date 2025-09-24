package org.balhom.statisticsapi.modules.transactionchanges.application

import jakarta.enterprise.context.ApplicationScoped
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
        val transactionStatisticToAddProps = if (props.type == TransactionTypeEnum.INCOME)
            TransactionStatisticToAddProps(
                currencyProfileId = props.currencyProfileId,
                date = props.date,
                oldDate = props.oldDate,
                category = props.category,
                oldCategory = props.oldCategory,
                incomeToAdd = props.amount,
                oldIncomeAdded = props.oldAmount,
                expensesToAdd = BigDecimal(0.0),
                oldExpensesAdded = null
            ) else
            TransactionStatisticToAddProps(
                currencyProfileId = props.currencyProfileId,
                date = props.date,
                oldDate = props.oldDate,
                category = props.category,
                oldCategory = props.oldCategory,
                incomeToAdd = BigDecimal(0.0),
                oldIncomeAdded = null,
                expensesToAdd = props.amount,
                oldExpensesAdded = props.oldAmount
            )

        val savingsStatisticToAddProps = SavingsStatisticToAddProps(
            currencyProfileId = props.currencyProfileId,
            type = props.type,
            date = props.date,
            oldDate = props.oldDate,
            amountToAdd = if (props.type == TransactionTypeEnum.INCOME) {
                props.amount
            } else {
                - props.amount
            },
            oldAmountAdded = props.oldAmount,
            monthlyGoal = props.cpGoalMonthlySaving,
            yearlyGoal = props.cpGoalYearlySaving,
        )

        transactionStatisticsService.add(
            transactionStatisticToAddProps
        )
        savingsStatisticsService.add(
            savingsStatisticToAddProps
        )
    }
}
