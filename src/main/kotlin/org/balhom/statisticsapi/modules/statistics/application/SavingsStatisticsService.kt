package org.balhom.statisticsapi.modules.statistics.application

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.common.data.props.ObjectIdUserProps
import org.balhom.statisticsapi.modules.currencyprofilechanges.application.CurrencyProfileService
import org.balhom.statisticsapi.modules.statistics.domain.models.MonthlySavingsStatistic
import org.balhom.statisticsapi.modules.statistics.domain.models.YearlySavingsStatistic
import org.balhom.statisticsapi.modules.statistics.domain.props.GoalSavingsStatisticToUpdateProps
import org.balhom.statisticsapi.modules.statistics.domain.props.MonthlyStatisticsProps
import org.balhom.statisticsapi.modules.statistics.domain.props.SavingsStatisticToAddProps
import org.balhom.statisticsapi.modules.statistics.domain.repositories.MonthlySavingsStatisticRepository
import org.balhom.statisticsapi.modules.statistics.domain.repositories.YearlySavingsStatisticRepository
import java.util.UUID

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

    fun updateGoals(props: GoalSavingsStatisticToUpdateProps) {
        val monthlyStatistic = monthlySavingsStatisticsRepository
            .findByCurrencyProfileIdAndMonthAndYear(
                currencyProfileId = props.currencyProfileId,
                month = props.date.monthValue,
                year = props.date.year
            )
        monthlyStatistic.goal = props.monthlyGoal

        monthlySavingsStatisticsRepository.save(monthlyStatistic)

        val yearlyStatistic = yearlySavingsStatisticsRepository
            .findByCurrencyProfileIdAndYear(
                currencyProfileId = props.currencyProfileId,
                year = props.date.year
            )
        yearlyStatistic.goal = props.yearlyGoal

        yearlySavingsStatisticsRepository.save(yearlyStatistic)
    }

    fun add(props: SavingsStatisticToAddProps) {
        // Add savings and goals for monthly statistic
        addToMonthlySavingsStatistic(props)

        // Add savings and goals for yearly statistic
        addToYearlySavingsStatistic(props)
    }

    private fun addToMonthlySavingsStatistic(
        props: SavingsStatisticToAddProps
    ) {
        val monthlyStatistic = monthlySavingsStatisticsRepository
            .findByCurrencyProfileIdAndMonthAndYear(
                currencyProfileId = props.currencyProfileId,
                month = props.date.monthValue,
                year = props.date.year
            )
        monthlyStatistic.savings += props.amountToAdd
        monthlyStatistic.goal = props.monthlyGoal

        monthlySavingsStatisticsRepository.save(monthlyStatistic)

        if (props.oldDate != null) {
            val oldMonthlyStatistic = if (
                props.date.monthValue == props.oldDate !!.monthValue
                && props.date.year == props.oldDate !!.year
            ) {
                monthlyStatistic
            } else {
                monthlySavingsStatisticsRepository
                    .findByCurrencyProfileIdAndMonthAndYear(
                        currencyProfileId = props.currencyProfileId,
                        month = props.oldDate !!.monthValue,
                        year = props.oldDate !!.year
                    )
            }

            if (props.oldAmountAdded != null) {
                oldMonthlyStatistic.savings -= props.oldAmountAdded !!
            }

            monthlySavingsStatisticsRepository.save(oldMonthlyStatistic)
        }
    }

    private fun addToYearlySavingsStatistic(
        props: SavingsStatisticToAddProps
    ) {
        val yearlyStatistic = yearlySavingsStatisticsRepository
            .findByCurrencyProfileIdAndYear(
                currencyProfileId = props.currencyProfileId,
                year = props.date.year
            )
        yearlyStatistic.savings += props.amountToAdd
        yearlyStatistic.goal = props.yearlyGoal

        yearlySavingsStatisticsRepository.save(yearlyStatistic)

        if (props.oldDate != null) {
            val oldYearlyStatistic = if (
                props.date.year == props.oldDate !!.year
            ) {
                yearlyStatistic
            } else {
                yearlySavingsStatisticsRepository
                    .findByCurrencyProfileIdAndYear(
                        currencyProfileId = props.currencyProfileId,
                        year = props.oldDate !!.year
                    )
            }

            if (props.oldAmountAdded != null) {
                oldYearlyStatistic.savings -= props.oldAmountAdded !!
            }

            yearlySavingsStatisticsRepository.save(oldYearlyStatistic)
        }
    }

    fun deleteAll(currencyProfileId: UUID) {
        monthlySavingsStatisticsRepository
            .deleteAllByCurrencyProfileId(currencyProfileId)

        yearlySavingsStatisticsRepository
            .deleteAllByCurrencyProfileId(currencyProfileId)
    }
}
