package org.balhom.statisticsapi.modules.currencyprofilechanges.application

import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.common.data.enums.EventChangeTypeEnum.DELETE
import org.balhom.statisticsapi.common.data.enums.EventChangeTypeEnum.UPDATE
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.props.CurrencyProfileChangeProps
import org.balhom.statisticsapi.modules.statistics.application.SavingsStatisticsService
import org.balhom.statisticsapi.modules.statistics.application.TransactionStatisticsService
import org.balhom.statisticsapi.modules.statistics.domain.props.GoalSavingsStatisticToUpdateProps
import java.time.LocalDateTime

@ApplicationScoped
class CurrencyProfileChangesService(
    private val transactionStatisticsService: TransactionStatisticsService,
    private val savingsStatisticsService: SavingsStatisticsService,
) {

    fun processChange(props: CurrencyProfileChangeProps) {
        when {
            props.eventChangeTypeEnum == UPDATE -> {
                savingsStatisticsService.updateGoals(
                    GoalSavingsStatisticToUpdateProps(
                        currencyProfileId = props.currencyProfileId,
                        date = LocalDateTime.now(),
                        monthlyGoal = props.monthlyGoal,
                        yearlyGoal = props.yearlyGoal,
                    )
                )
            }

            props.eventChangeTypeEnum == DELETE -> {
                transactionStatisticsService.deleteAll(
                    props.currencyProfileId
                )
                savingsStatisticsService.deleteAll(
                    props.currencyProfileId
                )
            }
        }
    }
}
