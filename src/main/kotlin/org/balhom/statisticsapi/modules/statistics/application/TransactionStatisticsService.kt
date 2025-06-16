package org.balhom.statisticsapi.modules.statistics.application

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.modules.currencyprofilechanges.application.CurrencyProfileService
import org.balhom.statisticsapi.modules.statistics.domain.models.DailyTransactionStatistic
import org.balhom.statisticsapi.modules.statistics.domain.models.MonthlyTransactionStatistic
import org.balhom.statisticsapi.modules.statistics.domain.props.DailyStatisticsProps
import org.balhom.statisticsapi.modules.statistics.domain.props.MonthlyStatisticsProps
import org.balhom.statisticsapi.modules.statistics.domain.props.SumTransactionStatisticProps
import org.balhom.statisticsapi.modules.statistics.domain.repositories.DailyTransactionStatisticRepository
import org.balhom.statisticsapi.modules.statistics.domain.repositories.MonthlyTransactionStatisticRepository
import java.util.*

@ApplicationScoped
class TransactionStatisticsService(
    private val currencyProfileService: CurrencyProfileService,
    private val monthlyTransactionStatisticRepository: MonthlyTransactionStatisticRepository,
    private val dailyTransactionStatisticRepository: DailyTransactionStatisticRepository
) {

    fun getMonthlyStatistics(props: MonthlyStatisticsProps):
            Uni<List<MonthlyTransactionStatistic>> {
        val userId = props.currencyProfileIdAndUser.userId
        val currencyProfileId = props.currencyProfileIdAndUser.id

        return currencyProfileService.getCurrencyProfileReferenceAndValidate(
            userId,
            currencyProfileId
        )
            .chain { _ ->
                monthlyTransactionStatisticRepository
                    .findAllByCurrencyProfileIdAndYear(
                        currencyProfileId,
                        props.year
                    )
            }
    }

    fun getDailyStatistics(props: DailyStatisticsProps):
            Uni<List<DailyTransactionStatistic>> {
        val userId = props.currencyProfileIdAndUser.userId
        val currencyProfileId = props.currencyProfileIdAndUser.id

        return currencyProfileService.getCurrencyProfileReferenceAndValidate(
            userId,
            currencyProfileId
        )
            .chain { _ ->
                dailyTransactionStatisticRepository
                    .findAllByCurrencyProfileIdAndMonthAndYear(
                        currencyProfileId,
                        props.month,
                        props.year
                    )
            }
    }

    fun addSum(props: SumTransactionStatisticProps) {
        // Add expenses and income sum for daily statistic
        val dailyStatistic = dailyTransactionStatisticRepository
            .findByCurrencyProfileIdAndDayAndMonthAndYear(
                currencyProfileId = props.currencyProfileId,
                day = props.date.dayOfMonth,
                month = props.date.monthValue,
                year = props.date.year
            )
        dailyStatistic.income += props.sumIncome
        dailyStatistic.expenses += props.sumExpenses

        // Add expenses and income sum for monthly statistic
        val monthlyStatistic = monthlyTransactionStatisticRepository
            .findByCurrencyProfileIdAndMonthAndYear(
                currencyProfileId = props.currencyProfileId,
                month = props.date.monthValue,
                year = props.date.year
            )
        monthlyStatistic.income += props.sumIncome
        monthlyStatistic.expenses += props.sumExpenses

        dailyTransactionStatisticRepository.save(
            dailyStatistic
        )
        monthlyTransactionStatisticRepository.save(
            monthlyStatistic
        )
    }

    fun deleteAll(currencyProfileId: UUID) {
        dailyTransactionStatisticRepository
            .deleteAllByCurrencyProfileId(currencyProfileId)

        monthlyTransactionStatisticRepository
            .deleteAllByCurrencyProfileId(currencyProfileId)
    }
}
