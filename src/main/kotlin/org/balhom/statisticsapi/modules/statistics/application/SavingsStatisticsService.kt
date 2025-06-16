package org.balhom.statisticsapi.modules.statistics.application

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.common.data.props.ObjectIdUserProps
import org.balhom.statisticsapi.modules.currencyprofilechanges.application.CurrencyProfileService
import org.balhom.statisticsapi.modules.statistics.domain.models.MonthlySavingsStatistic
import org.balhom.statisticsapi.modules.statistics.domain.models.YearlySavingsStatistic
import org.balhom.statisticsapi.modules.statistics.domain.props.MonthlyStatisticsProps
import org.balhom.statisticsapi.modules.statistics.domain.props.SumSavingsStatisticProps
import org.balhom.statisticsapi.modules.statistics.domain.repositories.MonthlySavingsStatisticRepository
import org.balhom.statisticsapi.modules.statistics.domain.repositories.YearlySavingsStatisticRepository
import java.util.*

@ApplicationScoped
class SavingsStatisticsService(
    private val currencyProfileService: CurrencyProfileService,
    private val monthlySavingsStatisticsRepository: MonthlySavingsStatisticRepository,
    private val yearlySavingsStatisticsRepository: YearlySavingsStatisticRepository
) {

    fun getMonthlyStatistics(props: MonthlyStatisticsProps):
            Uni<List<MonthlySavingsStatistic>> {
        val userId = props.currencyProfileIdAndUser.userId
        val currencyProfileId = props.currencyProfileIdAndUser.id

        return currencyProfileService.getCurrencyProfileReferenceAndValidate(
            userId,
            currencyProfileId
        )
            .chain { _ ->
                monthlySavingsStatisticsRepository
                    .findAllByCurrencyProfileIdAndYear(
                        currencyProfileId,
                        props.year
                    )
            }
    }

    fun getYearlyStatistics(props: ObjectIdUserProps):
            Uni<List<YearlySavingsStatistic>> {
        val userId = props.userId
        val currencyProfileId = props.id

        return currencyProfileService.getCurrencyProfileReferenceAndValidate(
            userId,
            currencyProfileId
        )
            .chain { _ ->
                yearlySavingsStatisticsRepository
                    .findAllByCurrencyProfileId(
                        currencyProfileId
                    )
            }
    }

    fun addSum(props: SumSavingsStatisticProps) {
        // Add sum and goals for monthly statistic
        val monthlyStatistic = monthlySavingsStatisticsRepository
            .findByCurrencyProfileIdAndMonthAndYear(
                currencyProfileId = props.currencyProfileId,
                month = props.date.monthValue,
                year = props.date.year
            )
        monthlyStatistic.savings += props.sum
        monthlyStatistic.goal = props.monthlyGoal

        // Add sum and goals for monthly statistic
        val yearlyStatistic = yearlySavingsStatisticsRepository
            .findByCurrencyProfileIdAndYear(
                currencyProfileId = props.currencyProfileId,
                year = props.date.year
            )
        yearlyStatistic.savings += props.sum
        yearlyStatistic.goal = props.yearlyGoal

        monthlySavingsStatisticsRepository.save(
            monthlyStatistic
        )
        yearlySavingsStatisticsRepository.save(
            yearlyStatistic
        )
    }

    fun deleteAll(currencyProfileId: UUID) {
        monthlySavingsStatisticsRepository
            .deleteAllByCurrencyProfileId(currencyProfileId)

        yearlySavingsStatisticsRepository
            .deleteAllByCurrencyProfileId(currencyProfileId)
    }
}
