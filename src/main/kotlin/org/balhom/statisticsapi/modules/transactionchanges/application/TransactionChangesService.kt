package org.balhom.statisticsapi.modules.transactionchanges.application

import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.modules.statistics.application.SavingsStatisticsService
import org.balhom.statisticsapi.modules.statistics.application.TransactionStatisticsService
import org.balhom.statisticsapi.modules.statistics.domain.props.SumSavingsStatisticProps
import org.balhom.statisticsapi.modules.statistics.domain.props.SumTransactionStatisticProps
import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum
import org.balhom.statisticsapi.modules.transactionchanges.domain.props.TransactionChangeProps
import java.math.BigDecimal

@ApplicationScoped
class TransactionChangesService(
    private val transactionStatisticsService: TransactionStatisticsService,
    private val savingsStatisticsService: SavingsStatisticsService,
) {

    fun processChange(props: TransactionChangeProps) {
        // If the transaction change is of type Income then the sum must be sum of income,
        // otherwise it must be sum of expense.
        val sumTransactionStatisticProps = if (props.type == TransactionTypeEnum.INCOME)
            SumTransactionStatisticProps(
                props.currencyProfileId,
                props.date,
                props.sum,
                BigDecimal(0.0),
            ) else
            SumTransactionStatisticProps(
                props.currencyProfileId,
                props.date,
                BigDecimal(0.0),
                props.sum,
            )
        // If the exchange rate of the transaction is Income then the sum
        // is positive for savings, otherwise it must be negative.
        val sumSavingsStatisticProps = if (props.type == TransactionTypeEnum.INCOME)
            SumSavingsStatisticProps(
                props.currencyProfileId,
                props.date,
                props.sum,
                props.cpGoalMonthlySaving,
                props.cpGoalYearlySaving,
            ) else
            SumSavingsStatisticProps(
                props.currencyProfileId,
                props.date,
                -props.sum,
                props.cpGoalMonthlySaving,
                props.cpGoalYearlySaving,
            )

        transactionStatisticsService.addSum(
            sumTransactionStatisticProps
        )
        savingsStatisticsService.addSum(
            sumSavingsStatisticProps
        )
    }
}
